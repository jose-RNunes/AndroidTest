package br.com.androidtest.data.datasource.mydata

import br.com.androidtest.core.data.AssetsReader
import br.com.androidtest.data.models.mydata.NPMyDataResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay

class NPMyDataDataSourceImpl(
    private val assetsReader: AssetsReader,
    private val gson: Gson
) : NPMyDataDataSource {
    override suspend fun getMyData(): NPMyDataResponse {
        delay(2000)
        val json = assetsReader.read("my_data/my_data_new.json")
        return gson.fromJson(json, NPMyDataResponse::class.java)
    }
}
