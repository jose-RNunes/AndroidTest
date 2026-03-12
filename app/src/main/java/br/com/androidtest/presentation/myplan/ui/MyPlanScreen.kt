package br.com.androidtest.presentation.myplan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.androidtest.domain.models.myplan.ItemExtraPlan
import br.com.androidtest.domain.models.myplan.MyPlanItemHeader
import br.com.androidtest.presentation.myplan.actions.MyPlanActionIntent
import br.com.androidtest.presentation.myplan.viewmodel.MyPlanViewModel
import br.com.androidtest.ui.LaunchOneTime
import br.com.androidtest.ui.Loading
import coil.compose.AsyncImage
import kotlin.String

@ExperimentalMaterial3Api
@Composable
fun MyPlanScreen(
    navController: NavController,
    viewModel: MyPlanViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchOneTime {
        viewModel.sendActionIntent(MyPlanActionIntent.Init)
    }

    MyPlanScreenContent(
        modifier = Modifier.fillMaxSize(),
        titleScreen = uiState.myPlanDomain.screnName,
        offerDisplay = uiState.myPlanDomain.offerDisplay,
        planValue = uiState.myPlanDomain.planValue,
        itemsHeader = uiState.itemsHeader,
        extraPlayTitle = uiState.myPlanDomain.extraPlay.title,
        listIconsApps = uiState.iconsExtrasPlan
    )

    Loading(
        isShow = uiState.isShowLoading
    )

}

@ExperimentalMaterial3Api
@Composable
private fun MyPlanScreenContent(
    modifier: Modifier,
    titleScreen: String,
    offerDisplay: String,
    planValue: String,
    itemsHeader: List<MyPlanItemHeader>,
    extraPlayTitle: String,
    listIconsApps: List<ItemExtraPlan>
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

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = offerDisplay,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = planValue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = titleScreen,
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(12.dp))

                if (itemsHeader.isNotEmpty()) {
                    HeaderSection(headers = itemsHeader)
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = extraPlayTitle,
                    fontSize = 16.sp,
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(start = 16.dp, top = 24.dp, bottom = 24.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listIconsApps.forEach { itemExtraPlan ->
                        AsyncImage(
                            model = itemExtraPlan.url,
                            contentDescription = itemExtraPlan.description,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun HeaderSection(headers: List<MyPlanItemHeader>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (headers.size >= 1) HeaderItem(headers[0])
        Spacer(modifier = Modifier.height(8.dp))
        if (headers.size >= 2) HeaderItem(headers[1])
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        if (headers.size >= 3) HeaderItem(headers[2], withSpace = true)
        if (headers.size >= 4) HeaderItem(headers[3], withSpace = true)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Composable
fun HeaderItem(item: MyPlanItemHeader, withSpace: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (withSpace) Arrangement.SpaceBetween else Arrangement.Start
    ) {
        Text(
            text = item.title,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        )

        Text(
            text = item.textValue,
            textAlign = if (item.alignment == "right")
                TextAlign.End
            else
                TextAlign.Start,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold

        )
    }
}


@ExperimentalMaterial3Api
@Composable
@Preview
private fun PreviewMyPlanScreenContent() {
    MyPlanScreenContent(
        modifier = Modifier.fillMaxSize(),
        titleScreen = "Plano Claro Flex",
        planValue = "R$ 39,00",
        offerDisplay = "20 GB",
        itemsHeader = arrayListOf(
            MyPlanItemHeader(
                title="Status:",
                textValue="Active",
                alignment="left",
                textColor="#1F1D1D"
            ),
            MyPlanItemHeader(
                title="Meu número:",
                textValue="(34) 99687-7876",
                alignment="left",
                textColor="#1F1D1D"
            ),
            MyPlanItemHeader(
                title="Plano",
                textValue="15GB",
                alignment="right",
                textColor="#1F1D1D"
            ),
            MyPlanItemHeader(
                title="Bônus para redes sociais",
                textValue="5GB",
                alignment="right",
                textColor="#1F1D1D"
            ),
        ),
        extraPlayTitle = " Apps inclusos",
        listIconsApps = arrayListOf(
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),
            ItemExtraPlan("desc", "https://mondrian.claro.com.br/brands/app/32px-alternative/tik-tok.png"),

        )
    )
}