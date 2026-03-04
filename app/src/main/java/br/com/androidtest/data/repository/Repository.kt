package br.com.androidtest.data.repository

import br.com.androidtest.data.service.ApiService
import br.com.androidtest.data.mapper.toDomainModel
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.domain.model.MyPlanScreen

interface MyDataRepository {
    suspend fun getMyData(): MyDataScreen
}

class NpMyDataRepository(
    private val api: ApiService,
) : MyDataRepository {

    override suspend fun getMyData(): MyDataScreen {
        return api.getMyDataNP().toDomainModel()
    }
}

class RwMyDataRepository(
    private val api: ApiService,
) : MyDataRepository {

    override suspend fun getMyData(): MyDataScreen {
        return api.getMyDataRW().toDomainModel()
    }
}

interface MyPlanRepository {
    suspend fun getMyPlan(): MyPlanScreen
}

class NpMyPlanRepository(
    private val api: ApiService,
) : MyPlanRepository {

    override suspend fun getMyPlan(): MyPlanScreen {
        return api.getMyPlanNP().toDomainModel()
    }
}

class RwMyPlanRepository(
    private val api: ApiService,
) : MyPlanRepository {

    override suspend fun getMyPlan(): MyPlanScreen {
        return api.getMyPlanRW().toDomainModel()
    }
}