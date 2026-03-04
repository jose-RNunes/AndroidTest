package br.com.androidtest.presentation.feature.mydata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.presentation.feature.mydata.mapper.toUiModel
import br.com.androidtest.presentation.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NPMyDataViewModel(
    private val getMyDataUseCase: GetMyDataUseCase,
) : ViewModel(), MyDataViewModelContract {

    private val _uiState = MutableStateFlow<UiState<MyDataUiModel>>(UiState.Loading)
    override val uiState: StateFlow<UiState<MyDataUiModel>> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<MyDataEffect>()
    override val effects: SharedFlow<MyDataEffect> = _effects.asSharedFlow()

    override fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)
            runCatching {
                getMyDataUseCase.execute().toUiModel()
            }.onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Erro ao carregar Meus Dados")
            }
        }
    }

    override fun onActionClicked(action: MyDataActionUiModel) {
        viewModelScope.launch {
            when (action.type) {
                MyDataUiActionType.DOWNLOAD_CONTRACT -> {
                    action.assetName?.let { _effects.emit(MyDataEffect.SharePdf(it)) }
                }
                MyDataUiActionType.PRIVACY -> {
                    action.url?.let { _effects.emit(MyDataEffect.OpenUrl(it)) }
                }
                MyDataUiActionType.MY_PLAN, MyDataUiActionType.EXIT -> {
                    // Tratado diretamente na tela
                }
            }
        }
    }
}

class RWMyDataViewModel(
    private val getMyDataUseCase: GetMyDataUseCase,
    private val privacyUrl: String,
) : ViewModel(), MyDataViewModelContract {

    private val _uiState = MutableStateFlow<UiState<MyDataUiModel>>(UiState.Loading)
    override val uiState: StateFlow<UiState<MyDataUiModel>> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<MyDataEffect>()
    override val effects: SharedFlow<MyDataEffect> = _effects.asSharedFlow()

    override fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(2000)
            runCatching {
                val domain = getMyDataUseCase.execute()
                MyDataUiModel(
                    title = "Meus dados",
                    avatarUrl = domain.user.avatarUrl,
                    userName = domain.user.name,
                    userCpf = domain.user.documentNumber,
                    userAge = domain.user.age,
                    actions = listOf(
                        MyDataActionUiModel(
                            id = MyDataUiActionType.MY_PLAN.name,
                            title = "Meu plano",
                            iconKey = "ic_myplan",
                            type = MyDataUiActionType.MY_PLAN,
                            assetName = null,
                            url = null,
                            exitDialog = null,
                        ),
                        MyDataActionUiModel(
                            id = MyDataUiActionType.DOWNLOAD_CONTRACT.name,
                            title = "Baixar contrato",
                            iconKey = "ic_document",
                            type = MyDataUiActionType.DOWNLOAD_CONTRACT,
                            assetName = "terms.pdf",
                            url = null,
                            exitDialog = null,
                        ),
                        MyDataActionUiModel(
                            id = MyDataUiActionType.PRIVACY.name,
                            title = "Privacidade",
                            iconKey = "ic_message",
                            type = MyDataUiActionType.PRIVACY,
                            assetName = null,
                            url = privacyUrl,
                            exitDialog = null,
                        ),
                        MyDataActionUiModel(
                            id = MyDataUiActionType.EXIT.name,
                            title = "Sair",
                            iconKey = "ic_block",
                            type = MyDataUiActionType.EXIT,
                            assetName = null,
                            url = null,
                            exitDialog = ExitDialogUiModel(
                                title = "Sair?",
                                subtitle = "Deseja realmente sair?",
                                confirmLabel = "Sim",
                                cancelLabel = "Nao",
                            ),
                        ),
                    ),
                )
            }.onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it.message ?: "Erro ao carregar Meus Dados")
            }
        }
    }

    override fun onActionClicked(action: MyDataActionUiModel) {
        viewModelScope.launch {
            when (action.type) {
                MyDataUiActionType.DOWNLOAD_CONTRACT -> {
                    action.assetName?.let { _effects.emit(MyDataEffect.SharePdf(it)) }
                }
                MyDataUiActionType.PRIVACY -> {
                    action.url?.let { _effects.emit(MyDataEffect.OpenUrl(it)) }
                }
                MyDataUiActionType.MY_PLAN, MyDataUiActionType.EXIT -> {}
            }
        }
    }
}