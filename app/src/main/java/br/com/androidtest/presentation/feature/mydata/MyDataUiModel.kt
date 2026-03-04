package br.com.androidtest.presentation.feature.mydata

import br.com.androidtest.presentation.UiState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class MyDataUiModel(
    val title: String,
    val avatarUrl: String,
    val userName: String,
    val userCpf: String,
    val userAge: String,
    val actions: List<MyDataActionUiModel>,
)

data class ExitDialogUiModel(
    val title: String,
    val subtitle: String?,
    val confirmLabel: String,
    val cancelLabel: String,
)

data class MyDataActionUiModel(
    val id: String,
    val title: String,
    val iconKey: String?,
    val type: MyDataUiActionType,
    val assetName: String?,
    val url: String?,
    val exitDialog: ExitDialogUiModel?,
)

enum class MyDataUiActionType {
    MY_PLAN,
    DOWNLOAD_CONTRACT,
    PRIVACY,
    EXIT,
}

sealed interface MyDataEffect {
    data class OpenUrl(val url: String) : MyDataEffect
    data class SharePdf(val assetName: String) : MyDataEffect
}

interface MyDataViewModelContract {
    val uiState: StateFlow<UiState<MyDataUiModel>>
    val effects: SharedFlow<MyDataEffect>
    fun load()
    fun onActionClicked(action: MyDataActionUiModel)
}