package br.com.androidtest.core.util.share

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

object ShareUtil {

    fun shareTermsPdf(context: Context?, file: File) {
        context?.let { itCtx ->
            val uri = FileProvider.getUriForFile(
                itCtx,
                "${itCtx.packageName}.provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(
                Intent.createChooser(shareIntent, "Compartilhar contrato")
            )
        }
    }
}
