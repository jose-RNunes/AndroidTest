package br.com.androidtest.core.util.file

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

object FileUtil {

    fun copyAssetToCache(context: Context?, assetName: String): File? {
        return try {
            val file = File(context?.cacheDir, assetName)

            context?.assets?.open(assetName).use { input ->
                FileOutputStream(file).use { output ->
                    input?.copyTo(output)
                }
            }

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
