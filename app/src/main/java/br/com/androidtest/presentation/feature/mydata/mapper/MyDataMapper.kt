package br.com.androidtest.presentation.feature.mydata.mapper

import br.com.androidtest.domain.model.MyDataActionType
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.presentation.feature.mydata.ExitDialogUiModel
import br.com.androidtest.presentation.feature.mydata.MyDataActionUiModel
import br.com.androidtest.presentation.feature.mydata.MyDataUiActionType
import br.com.androidtest.presentation.feature.mydata.MyDataUiModel

fun MyDataScreen.toUiModel(): MyDataUiModel {
    return MyDataUiModel(
        title = title,
        avatarUrl = user.avatarUrl,
        userName = user.name,
        userCpf = user.documentNumber,
        userAge = user.age,
        actions = actions.map { action ->
            MyDataActionUiModel(
                id = action.type.name,
                title = action.title,
                iconKey = action.iconKey,
                type = when (action.type) {
                    MyDataActionType.MY_PLAN -> MyDataUiActionType.MY_PLAN
                    MyDataActionType.DOWNLOAD_CONTRACT -> MyDataUiActionType.DOWNLOAD_CONTRACT
                    MyDataActionType.PRIVACY -> MyDataUiActionType.PRIVACY
                    MyDataActionType.EXIT -> MyDataUiActionType.EXIT
                },
                assetName = action.assetName,
                url = action.url,
                exitDialog = action.exitDialogConfig?.let {
                    ExitDialogUiModel(
                        title = it.title,
                        subtitle = it.subtitle,
                        confirmLabel = it.confirmLabel,
                        cancelLabel = it.cancelLabel,
                    )
                },
            )
        },
    )
}