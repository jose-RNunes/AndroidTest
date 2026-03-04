package br.com.androidtest.domain.usecase

import br.com.androidtest.data.repository.MyPlanRepository
import br.com.androidtest.domain.model.MyPlanScreen

class GetMyPlanUseCase(
    private val repository: MyPlanRepository,
) {
    suspend fun execute(): MyPlanScreen = repository.getMyPlan()
}