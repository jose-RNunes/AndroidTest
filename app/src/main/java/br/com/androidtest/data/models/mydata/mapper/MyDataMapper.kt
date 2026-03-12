package br.com.androidtest.data.models.mydata.mapper

import br.com.androidtest.data.models.mydata.NPMyDataResponse
import br.com.androidtest.data.models.mydata.OptionDto
import br.com.androidtest.data.models.mydata.old.RWMyDataResponse
import br.com.androidtest.domain.models.mydata.MyDataAction
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.models.mydata.MyDataOptionDomain

fun NPMyDataResponse.toDomain() : MyDataDomain {
    return MyDataDomain(
        name = screen.profile.name,
        age = screen.profile.age,
        documentNumber = screen.profile.documentNumber,
        avatarUrl = screen.profile.avatarUrl,
        options = screen.options.map { it.toDomain() },
        titleScreen = screen.title
    )
}

fun OptionDto.toDomain() : MyDataOptionDomain {
    return MyDataOptionDomain(
        iconUrl = iconUrl,
        iconType = iconType,
        title = title,
        externalUrl = url,
        action = action.toMyDataAction()
    )
}

fun RWMyDataResponse.toDomain() : MyDataDomain {
    return MyDataDomain(
        name = content.user.name,
        age = content.user.age,
        documentNumber = content.user.documentNumber,
        avatarUrl = content.user.avatarUrl,
        options = listOf()
    )
}

fun String.toMyDataAction(): MyDataAction {
    return try {
        MyDataAction.valueOf(this)
    } catch (e: IllegalArgumentException) {
        MyDataAction.UNKNOWN
    }
}

