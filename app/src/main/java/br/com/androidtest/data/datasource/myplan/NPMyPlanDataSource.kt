package br.com.androidtest.data.datasource.myplan

import br.com.androidtest.data.models.myplan.MyPlanResponse

interface NPMyPlanDataSource {
    suspend fun getMyPlan(): MyPlanResponse
}