package br.com.androidtest.data.datasource.mydata

import br.com.androidtest.core.data.AssetsReader
import br.com.androidtest.data.models.mydata.old.RWMyDataResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay

class RWMyDataDataSourceImpl(
    private val assetsReader: AssetsReader,
    private val gson: Gson
) : RWMyDataDataSource {

    override suspend fun getMyData(): RWMyDataResponse {
        delay(2000)
        val json = assetsReader.read("my_data/my_data_old.json")
        return gson.fromJson(json, RWMyDataResponse::class.java)
    }
}
