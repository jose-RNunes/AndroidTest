package br.com.androidtest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import br.com.androidtest.R

@Composable
fun ExitConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    cancelText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.dialog_corner_radius)),
                )
                .padding(dimensionResource(R.dimen.dialog_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dialog_spacing)),
        ) {
            Text(text = title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            Text(text = message)

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.dialog_button_corner_radius)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.claro_red_dark),
                    contentColor = Color.White,
                ),
            ) {
                Text(text = confirmText)
            }

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(R.dimen.dialog_button_corner_radius)),
                border = BorderStroke(dimensionResource(R.dimen.dialog_button_border_width), colorResource(R.color.button_outlined_border)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = colorResource(R.color.button_outlined_background),
                    contentColor = Color.Black,
                ),
            ) {
                Text(text = cancelText)
            }
        }
    }
}