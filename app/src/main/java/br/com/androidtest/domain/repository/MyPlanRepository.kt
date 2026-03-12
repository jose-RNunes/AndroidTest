package br.com.androidtest.domain.repository

import br.com.androidtest.domain.models.myplan.MyPlanDomain

interface MyPlanRepository {
    suspend fun getMyPlan() : MyPlanDomain
}