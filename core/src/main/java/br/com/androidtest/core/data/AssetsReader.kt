package br.com.androidtest.core.data

import android.content.Context

class AssetsReader(
    private val context: Context
) {
    fun read(fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }
}
