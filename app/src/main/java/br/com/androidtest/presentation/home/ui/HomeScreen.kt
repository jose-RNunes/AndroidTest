package br.com.androidtest.presentation.home.ui

import android.provider.SyncStateContract
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.androidtest.R
import br.com.androidtest.domain.models.ItemHome
import br.com.androidtest.navigation.Routes
import br.com.androidtest.presentation.home.actions.HomeActionIntent
import br.com.androidtest.presentation.home.viewmodel.HomeViewModel
import br.com.androidtest.ui.BottomSheetMessageTwoButtons
import br.com.androidtest.ui.LaunchOneTime
import org.koin.androidx.compose.koinViewModel

val ID_ITEM_NEW_PLATFORM = 1
val ID_ITEM_OLD_PLATFORM = 2

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchOneTime {
        viewModel.sendActionIntent(HomeActionIntent.Init)
    }

    BackHandler {
        viewModel.sendActionIntent(HomeActionIntent.ShowMessageLogOut)
    }

    HomeScreenContent(
        modifier = Modifier.fillMaxSize(),
        listItems = uiState.listItemsHome,
        onClickItem = { itemHome ->
            when (itemHome.id) {
                ID_ITEM_NEW_PLATFORM -> {
                    navController.navigate(
                        Routes.MyData.createRoute("NP")
                    )
                }

                ID_ITEM_OLD_PLATFORM -> {
                    navController.navigate(
                        Routes.MyData.createRoute("RW")
                    )
                }
            }
        }
    )

    val activity = LocalActivity.current

    BottomSheetMessageTwoButtons(
        isShow = uiState.isMessageLogOut,
        icon = 0,
        title = R.string.title_question_exit,
        message = R.string.message_exit,
        titleBtnMain =  R.string.yes,
        titleSecondBtn = R.string.no,
        onDismiss = {
            viewModel.sendActionIntent(HomeActionIntent.HideMessageLogOut)
        },
        onClickBtnMain = {
            activity?.finish()
        }
    )

}

@ExperimentalMaterial3Api
@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    listItems: List<ItemHome>,
    onClickItem: (ItemHome) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(top = 120.dp)
    ) {
        LazyColumn {
            itemsIndexed(items = listItems) { index, item ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f).clickable {
                            onClickItem.invoke(item)
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(modifier = Modifier.width(45.dp).height(45.dp), onClick = { onClickItem.invoke(item) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.go_item_menu),
                            tint = Color.Black
                        )
                    }
                }

                HorizontalDivider()
            }
        }
    }
}
