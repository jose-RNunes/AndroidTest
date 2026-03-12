package br.com.androidtest.util

import android.content.Context

class ResourceResolver(
    private val context: Context
) {
    fun getDrawableId(name: String?): Int {
        if (name.isNullOrBlank()) return 0

        return context.resources.getIdentifier(
            name,
            "drawable",
            context.packageName
        )
    }
}
