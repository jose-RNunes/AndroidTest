package br.com.androidtest.data.datasource.myplan

import br.com.androidtest.core.data.AssetsReader
import br.com.androidtest.data.models.myplan.old.OldPlansResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay

class RWMyPlanDataSourceImpl(
    private val assetsReader: AssetsReader,
    private val gson: Gson
) : RWMyPlanDataSource {
    override suspend fun getMyPlan(): OldPlansResponse {
        delay(2000)
        val json = assetsReader.read("my_plan/my_plan_old.json")
        return gson.fromJson(json, OldPlansResponse::class.java)
    }
}
