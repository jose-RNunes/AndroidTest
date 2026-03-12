package br.com.androidtest.data.datasource.myplan

import br.com.androidtest.data.models.myplan.old.OldPlansResponse

interface RWMyPlanDataSource {
    suspend fun getMyPlan(): OldPlansResponse
}
