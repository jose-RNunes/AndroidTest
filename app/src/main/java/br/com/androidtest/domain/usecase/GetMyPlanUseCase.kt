package br.com.androidtest.domain.usecase

import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.repository.MyPlanRepository

class GetMyPlanUseCase(
    private val myPlanRepository: MyPlanRepository
) {
    suspend fun invoke(): MyPlanDomain {
        return myPlanRepository.getMyPlan()
    }
}
