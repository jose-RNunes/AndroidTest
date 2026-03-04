package br.com.androidtest.domain.model

data class User(
    val id: String,
    val name: String,
    val documentNumber: String,
    val age: String,
    val avatarUrl: String,
)

enum class MyDataActionType {
    MY_PLAN,
    DOWNLOAD_CONTRACT,
    PRIVACY,
    EXIT,
}

data class ExitDialogConfig(
    val title: String,
    val subtitle: String?,
    val confirmLabel: String,
    val cancelLabel: String,
)

data class MyDataAction(
    val type: MyDataActionType,
    val title: String,
    val iconKey: String?,
    val assetName: String?,
    val url: String?,
    val exitDialogConfig: ExitDialogConfig?,
)

data class MyDataScreen(
    val title: String,
    val user: User,
    val actions: List<MyDataAction>,
)

