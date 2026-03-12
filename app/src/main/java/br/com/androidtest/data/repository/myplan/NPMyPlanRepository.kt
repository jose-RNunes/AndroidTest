package br.com.androidtest.data.repository.myplan

import br.com.androidtest.data.datasource.myplan.NPMyPlanDataSource
import br.com.androidtest.data.models.myplan.mapper.toDomain
import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.repository.MyPlanRepository

class NPMyPlanRepository (
    private val NPMyPlanDataSource: NPMyPlanDataSource
) : MyPlanRepository {

    override suspend fun getMyPlan(): MyPlanDomain {
        val response = NPMyPlanDataSource.getMyPlan()
        return response.toDomain()
    }
}