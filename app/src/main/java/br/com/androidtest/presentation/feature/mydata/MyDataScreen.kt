package br.com.androidtest.presentation.feature.mydata

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import br.com.androidtest.R
import br.com.androidtest.presentation.UiState
import br.com.androidtest.presentation.components.ExitConfirmationDialog
import br.com.androidtest.presentation.feedback.LoadingView
import br.com.androidtest.utils.shareAssetPdf
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun MyDataScreen(
    uiState: UiState<MyDataUiModel>,
    effects: SharedFlow<MyDataEffect>,
    onActionClick: (MyDataActionUiModel) -> Unit,
    onGoToMyPlan: () -> Unit,
    onBackToHome: () -> Unit,
    onExitApp: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(effects) {
        effects.collect { effect ->
            when (effect) {
                is MyDataEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(effect.url))
                    context.startActivity(intent)
                }
                is MyDataEffect.SharePdf -> {
                    shareAssetPdf(context, effect.assetName)
                }
            }
        }
    }

    BackHandler(onBack = onBackToHome)

    when (uiState) {
        UiState.Loading -> LoadingView()
        is UiState.Error -> ErrorDataContent(uiState.message, onBackToHome)
        is UiState.Success -> MyDataContent(
            model = uiState.data,
            onActionClick = onActionClick,
            onGoToMyPlan = onGoToMyPlan,
            onBackToHome = onBackToHome,
            onExitApp = onExitApp,
        )
    }
}

@Composable
private fun MyDataContent(
    model: MyDataUiModel,
    onActionClick: (MyDataActionUiModel) -> Unit,
    onGoToMyPlan: () -> Unit,
    onBackToHome: () -> Unit,
    onExitApp: () -> Unit,
) {
    var exitDialogAction by remember { mutableStateOf<MyDataActionUiModel?>(null) }

    Scaffold(
        containerColor = Color.White,
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
                        .clickable { onBackToHome() },
                    tint = Color.White,
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(R.dimen.spacing_md), vertical = dimensionResource(R.dimen.spacing_md)),
        ) {
            item {
                Text(
                    text = model.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_md)))
                UserProfileCard(model = model)
            }

            items(model.actions) { action ->
                ActionItem(
                    action = action,
                    onClick = {
                        when (action.type) {
                            MyDataUiActionType.MY_PLAN -> onGoToMyPlan()
                            MyDataUiActionType.EXIT -> exitDialogAction = action
                            else -> onActionClick(action)
                        }
                    },
                )
                if (action != model.actions.last()) {
                    HorizontalDivider(color = colorResource(R.color.divider))
                }
            }
        }
    }

    exitDialogAction?.exitDialog?.let { dialog ->
        ExitConfirmationDialog(
            title = dialog.title,
            message = dialog.subtitle.orEmpty(),
            confirmText = dialog.confirmLabel,
            cancelText = dialog.cancelLabel,
            onConfirm = {
                exitDialogAction = null
                onExitApp()
            },
            onCancel = { exitDialogAction = null },
        )
    }
}

@Composable
private fun UserProfileCard(model: MyDataUiModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        AsyncImage(
            model = model.avatarUrl,
            contentDescription = stringResource(R.string.my_data_cd_avatar, model.userName),
            placeholder = painterResource(R.drawable.ic_avatar),
            error = painterResource(R.drawable.ic_avatar),
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size_avatar))
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_md)))
        LabelValueText(label = stringResource(R.string.my_data_label_name), value = model.userName)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xs)))
        LabelValueText(label = stringResource(R.string.my_data_label_cpf), value = model.userCpf)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_xs)))
        LabelValueText(label = stringResource(R.string.my_data_label_age), value = model.userAge)
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_md)))
        HorizontalDivider(color = colorResource(R.color.divider))
    }
}

@Composable
private fun LabelValueText(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            append("$label: ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(value)
            }
        },
        style = MaterialTheme.typography.bodyLarge,
    )
}

@DrawableRes
private fun iconKeyToDrawable(iconKey: String?): Int? = when (iconKey) {
    "ic_document", "ic_myplan" -> R.drawable.ic_document
    "ic_message" -> R.drawable.ic_message
    "ic_block" -> R.drawable.ic_block
    "ic_download" -> R.drawable.ic_download
    else -> null
}

@Composable
private fun ActionItem(
    action: MyDataActionUiModel,
    onClick: () -> Unit,
) {
    val iconRes = iconKeyToDrawable(action.iconKey)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(R.dimen.spacing_lg)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = colorResource(R.color.claro_red),
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),
            )
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.action_item_icon_spacing)))
        }
        Text(
            text = action.title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = colorResource(R.color.icon_tint_secondary),
        )
    }
}

@Composable
private fun ErrorDataContent(
    message: String,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(dimensionResource(R.dimen.spacing_xl)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.my_data_error))
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
