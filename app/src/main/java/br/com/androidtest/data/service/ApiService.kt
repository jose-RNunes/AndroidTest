package br.com.androidtest.data.service

import br.com.androidtest.data.model.NPMyDataResponseDto
import br.com.androidtest.data.model.NPMyPlanResponseDto
import br.com.androidtest.data.model.RWMyDataResponseDto
import br.com.androidtest.data.model.RWMyPlanResponseDto
import retrofit2.http.GET

interface ApiService {

    @GET("my_data/my_data_new.json")
    suspend fun getMyDataNP(): NPMyDataResponseDto

    @GET("my_data/my_data_old.json")
    suspend fun getMyDataRW(): RWMyDataResponseDto

    @GET("my_plan/my_plan_new.json")
    suspend fun getMyPlanNP(): NPMyPlanResponseDto

    @GET("my_plan/my_plan_old.json")
    suspend fun getMyPlanRW(): RWMyPlanResponseDto
}