package br.com.androidtest.presentation.feature.myplan

import br.com.androidtest.presentation.UiState
import kotlinx.coroutines.flow.StateFlow

data class MyPlanUiModel(
    val status: String,
    val phoneNumber: String,
    val planValue: String,
    val planGb: Int,
    val bonusGb: Int,
    val apps: List<PlanAppUiModel>,
)

data class PlanAppUiModel(
    val name: String,
    val iconUrl: String,
)

interface MyPlanViewModelContract {
    val uiState: StateFlow<UiState<MyPlanUiModel>>
    fun load()
}