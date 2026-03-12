package br.com.androidtest.presentation.mydata.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.androidtest.R
import br.com.androidtest.core.maskCpf
import br.com.androidtest.core.util.file.FileUtil
import br.com.androidtest.core.util.share.Browser
import br.com.androidtest.core.util.share.ShareUtil
import br.com.androidtest.domain.models.mydata.ItemMyData
import br.com.androidtest.domain.models.mydata.MyDataAction
import br.com.androidtest.navigation.Routes
import br.com.androidtest.presentation.mydata.action.MyDataActionIntent
import br.com.androidtest.presentation.mydata.event.MyDataEvent
import br.com.androidtest.presentation.mydata.viewmodel.MyDataViewModel
import br.com.androidtest.ui.BottomSheetMessageTwoButtons
import br.com.androidtest.ui.LaunchOneTime
import br.com.androidtest.ui.Loading
import coil.compose.AsyncImage

@ExperimentalMaterial3Api
@Composable
fun MyDataProfileScreen(
    navController: NavController,
    viewModel: MyDataViewModel,
    platform_prefix: String
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchOneTime {
        viewModel.sendActionIntent(MyDataActionIntent.Init)
    }

    MyDataProfileScreenContent(
        modifier = Modifier.fillMaxSize(),
        titleScreen = uiState.myData.titleScreen,
        avatar = uiState.myData.avatarUrl,
        name = uiState.myData.name,
        document = uiState.myData.documentNumber.maskCpf(),
        age = uiState.myData.age,
        items = uiState.itemsMyData,
        onClickItem = { itemClick ->
            viewModel.sendActionIntent(MyDataActionIntent.HandlerActionItemClicked(itemClick))
        }
    )

    Loading(
        isShow = uiState.isShowLoading
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
            viewModel.sendActionIntent(MyDataActionIntent.HideMessageLogOut)
        },
        onClickBtnMain = {
            activity?.finish()
        }
    )

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event){
                is MyDataEvent.ShareTerms -> {
                    FileUtil.copyAssetToCache(
                        context,
                        "terms.pdf"
                    )?.let {
                        ShareUtil.shareTermsPdf(context, it)
                    }
                }

                is MyDataEvent.OpenUrl -> {
                    Browser.open(context, event.url)
                }

                is MyDataEvent.GoToMyPlan -> {
                    navController.navigate(
                        Routes.MyPlan.createRoute(platform_prefix + "_plan")
                    )
                }
            }
        }
    }
}

@Composable
private fun MyDataProfileScreenContent(
    modifier: Modifier,
    titleScreen: String,
    avatar: String,
    name: String,
    document: String,
    age: String,
    items: List<ItemMyData>,
    onClickItem: (ItemMyData) -> Unit = {}
) {
    Column(modifier = modifier.background(Color.White)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Color.Red)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {}

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                text = titleScreen,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = avatar,
                    contentDescription = stringResource(R.string.avatar_profile),
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_avatar),
                    error = painterResource(R.drawable.ic_avatar)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.label_name),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.label_document),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    text = document,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.label_age),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp),
                    text = age,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(items = items) { index, item ->
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                        onClickItem.invoke(item)
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(modifier = Modifier.width(45.dp).height(45.dp), onClick = { onClickItem.invoke(item) }) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,

                        )
                    }

                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f)
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
            }
        }
    }

}

@Preview
@Composable
private fun PreviewMyDataProfileScreenContent() {
    MyDataProfileScreenContent(
        modifier = Modifier.fillMaxSize(),
        titleScreen = "Meus dados",
        avatar = "https://gravatar.com/avatar/528fc61d588a9afd3b1567583aeef2a2?s=400&d=robohash&r=x",
        name = "Daniel Monteiro de Sousa",
        document = "523.889.632-12",
        age = "22",
        arrayListOf(
            ItemMyData(R.drawable.ic_document, "Teste 1", action = MyDataAction.MY_PLAN),
            ItemMyData(R.drawable.ic_download, "Teste 2", action = MyDataAction.SHARE_CONTRACT),
            ItemMyData(R.drawable.ic_message, "Teste 2", action = MyDataAction.WEB),
            ItemMyData(R.drawable.ic_block, "Teste 2", action = MyDataAction.EXIT)
            )
    )
}
