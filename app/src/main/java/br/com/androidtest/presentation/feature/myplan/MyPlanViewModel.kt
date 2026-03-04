package br.com.androidtest.presentation.feature.myplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.feature.myplan.mapper.toUiModel
import br.com.androidtest.presentation.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NPMyPlanViewModel(
    private val getMyPlanUseCase: GetMyPlanUseCase,
) : ViewModel(), MyPlanViewModelContract {

    private val _uiState = MutableStateFlow<UiState<MyPlanUiModel>>(UiState.Loading)
    override val uiState: StateFlow<UiState<MyPlanUiModel>> = _uiState.asStateFlow()

    override fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)
            runCatching {
                getMyPlanUseCase.execute().toUiModel()
            }.onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Erro ao carregar Meu Plano")
            }
        }
    }
}

class RWMyPlanViewModel(
    private val getMyPlanUseCase: GetMyPlanUseCase,
) : ViewModel(), MyPlanViewModelContract {

    private val _uiState = MutableStateFlow<UiState<MyPlanUiModel>>(UiState.Loading)
    override val uiState: StateFlow<UiState<MyPlanUiModel>> = _uiState.asStateFlow()

    override fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)
            runCatching {
                val domain = getMyPlanUseCase.execute()
                MyPlanUiModel(
                    status = domain.header.status,
                    phoneNumber = domain.header.phoneNumber,
                    planValue = domain.header.planValue,
                    planGb = domain.quota.planGb,
                    bonusGb = domain.quota.bonusGb,
                    apps = domain.extraApps.map { PlanAppUiModel(name = it.name, iconUrl = it.iconUrl) },
                )
            }.onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Erro ao carregar Meu Plano")
            }
        }
    }
}