package br.com.androidtest.presentation.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import br.com.androidtest.R
import br.com.androidtest.presentation.components.ExitConfirmationDialog
import br.com.androidtest.utils.Platform

@Composable
fun HomeScreen(
    onPlatformSelected: (Platform) -> Unit,
    onExitConfirmed: () -> Unit,
) {
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimensionResource(R.dimen.spacing_md)),
        verticalArrangement = Arrangement.Center,
    ) {
        HorizontalDivider(color = colorResource(R.color.divider))
        PlatformItem(
            text = stringResource(R.string.platform_new),
            onClick = {
                onPlatformSelected(Platform.NP)
            },
        )
        HorizontalDivider(color = colorResource(R.color.divider))
        PlatformItem(
            text = stringResource(R.string.platform_old),
            onClick = {
                onPlatformSelected(Platform.RW)
            },
        )
        HorizontalDivider(color = colorResource(R.color.divider))
    }

    if (showExitDialog) {
        ExitConfirmationDialog(
            title = stringResource(R.string.exit_dialog_title),
            message = stringResource(R.string.exit_dialog_message),
            confirmText = stringResource(R.string.action_confirm),
            cancelText = stringResource(R.string.action_cancel),
            onConfirm = {
                showExitDialog = false
                onExitConfirmed()
            },
            onCancel = { showExitDialog = false },
        )
    }
}

@Composable
private fun PlatformItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = dimensionResource(R.dimen.spacing_lg)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
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
