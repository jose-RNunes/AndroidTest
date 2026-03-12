package br.com.androidtest.data.repository.myplan

import br.com.androidtest.data.datasource.myplan.RWMyPlanDataSource
import br.com.androidtest.data.models.myplan.mapper.toDomain
import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.repository.MyPlanRepository

class RWMyPlanRepository(
    private val dataSource: RWMyPlanDataSource
) : MyPlanRepository {
    override suspend fun getMyPlan(): MyPlanDomain {
        val response = dataSource.getMyPlan()
        return response.toDomain()
    }
}
