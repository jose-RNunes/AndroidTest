package br.com.androidtest.data.remote

import android.content.Context
import br.com.androidtest.data.remote.interceptor.AssetInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://mock.local/"

object RetrofitProvider {

    fun create(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(AssetInterceptor(context))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}