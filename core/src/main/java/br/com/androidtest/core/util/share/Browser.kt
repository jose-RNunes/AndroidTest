package br.com.androidtest.core.util.share

import android.content.Context
import android.content.Intent
import android.net.Uri

object Browser {
    fun open(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}