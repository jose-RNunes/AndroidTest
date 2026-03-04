package br.com.androidtest.data.mapper

import br.com.androidtest.data.model.NPMyDataResponseDto
import br.com.androidtest.data.model.RWMyDataResponseDto
import br.com.androidtest.domain.model.ExitDialogConfig
import br.com.androidtest.domain.model.MyDataAction
import br.com.androidtest.domain.model.MyDataActionType
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.domain.model.User

fun NPMyDataResponseDto.toDomainModel(): MyDataScreen {
    val actions = screen.options.mapNotNull { option ->
        val type = when (option.action) {
            "MY_PLAN" -> MyDataActionType.MY_PLAN
            "SHARE_CONTRACT" -> MyDataActionType.DOWNLOAD_CONTRACT
            "WEB" -> MyDataActionType.PRIVACY
            "EXIT" -> MyDataActionType.EXIT
            else -> null
        } ?: return@mapNotNull null

        MyDataAction(
            type = type,
            title = option.title,
            iconKey = option.iconUrl ?: option.iconType,
            assetName = option.assetName,
            url = option.url,
            exitDialogConfig = option.modal?.let {
                ExitDialogConfig(
                    title = it.title,
                    subtitle = it.subtitle,
                    confirmLabel = it.buttons[0].title,
                    cancelLabel = it.buttons[1].title,
                )
            },
        )
    }

    return MyDataScreen(
        title = screen.title,
        user = User(
            id = screen.profile.id,
            name = screen.profile.name,
            documentNumber = screen.profile.documentNumber,
            age = screen.profile.age,
            avatarUrl = screen.profile.avatarUrl,
        ),
        actions = actions,
    )
}

fun RWMyDataResponseDto.toDomainModel(): MyDataScreen {
    return MyDataScreen(
        title = "Meus dados",
        user = User(
            id = content.user.id,
            name = content.user.name,
            documentNumber = content.user.documentNumber,
            age = content.user.age,
            avatarUrl = content.user.avatarUrl,
        ),
        actions = emptyList(),
    )
}