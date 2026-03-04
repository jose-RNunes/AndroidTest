package br.com.androidtest.presentation.feature.myplan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import br.com.androidtest.R
import br.com.androidtest.presentation.UiState
import br.com.androidtest.presentation.feedback.LoadingView
import coil.compose.AsyncImage

@Composable
fun MyPlanScreen(
    uiState: UiState<MyPlanUiModel>,
    onBack: () -> Unit,
) {
    BackHandler(onBack = onBack)

    when (uiState) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> ErrorPlanContent(uiState.message, onBack)
        is UiState.Success -> MyPlanContent(model = uiState.data, onBack = onBack)
    }
}

@Composable
private fun MyPlanContent(
    model: MyPlanUiModel,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = colorResource(R.color.background_light),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.top_bar_height))
                    .background(colorResource(R.color.claro_red))
                    .padding(start = dimensionResource(R.dimen.top_bar_padding_start), top = dimensionResource(R.dimen.spacing_md)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.icon_size))
                        .clickable { onBack() },
                    tint = Color.White,
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(start = dimensionResource(R.dimen.spacing_md), end = dimensionResource(R.dimen.spacing_md), top = dimensionResource(R.dimen.spacing_md), bottom = dimensionResource(R.dimen.spacing_md)),
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.my_plan_screen_title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${model.planGb + model.bonusGb}GB",
                        color = colorResource(R.color.claro_red),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip,
                    )
                    Text(
                        text = model.planValue,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            Text(
                text = stringResource(R.string.my_plan_status, model.status),
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = stringResource(R.string.my_plan_phone, model.phoneNumber),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_sm), bottom = dimensionResource(R.dimen.divider_margin_bottom)),
                color = colorResource(R.color.divider),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(stringResource(R.string.my_plan_gb_label), style = MaterialTheme.typography.titleMedium)
                    Text(stringResource(R.string.my_plan_bonus_label), style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${model.planGb}GB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        "${model.bonusGb}GB",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_sm), bottom = dimensionResource(R.dimen.divider_margin_bottom)),
                color = colorResource(R.color.divider),
            )

            Text(
                text = stringResource(R.string.my_plan_apps_title),
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_sm_plus)))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.background_light), shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)))
                    .border(
                        width = dimensionResource(R.dimen.card_border_width),
                        color = colorResource(R.color.card_border),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
                    )
                    .padding(vertical = dimensionResource(R.dimen.card_padding_vertical)),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                model.apps.take(6).forEach { app ->
                    AsyncImage(
                        model = app.iconUrl,
                        contentDescription = app.name,
                        modifier = Modifier.size(dimensionResource(R.dimen.icon_size_app)),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorPlanContent(
    message: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_light))
            .padding(dimensionResource(R.dimen.spacing_xl)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.my_plan_error))
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_sm)))
        Text(text = message, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_md)))
        Text(
            text = stringResource(R.string.action_back),
            color = colorResource(R.color.claro_red),
            modifier = Modifier.clickable(onClick = onBack),
        )
    }
}