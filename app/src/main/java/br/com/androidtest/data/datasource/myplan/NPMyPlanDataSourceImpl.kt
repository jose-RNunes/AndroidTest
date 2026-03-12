package br.com.androidtest.data.datasource.myplan

import br.com.androidtest.core.data.AssetsReader
import br.com.androidtest.data.models.myplan.MyPlanResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay

class NPMyPlanDataSourceImpl(
    private val assetsReader: AssetsReader,
    private val gson: Gson
) : NPMyPlanDataSource {

    override suspend fun getMyPlan(): MyPlanResponse {
        delay(2000)
        val json = assetsReader.read("my_plan/my_plan_new.json")
        return gson.fromJson(json, MyPlanResponse::class.java)
    }
}