package br.com.androidtest.data.remote.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AssetInterceptor(
    private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val assetPath = chain.request().url.encodedPath.trimStart('/')
        val json = context.assets.open(assetPath).bufferedReader().use { it.readText() }

        return Response.Builder()
            .code(200)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .message("OK")
            .body(json.toResponseBody("application/json; charset=utf-8".toMediaType()))
            .build()
    }
}