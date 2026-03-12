package br.com.androidtest.data.models.myplan.mapper

import br.com.androidtest.data.models.myplan.ExtraPlayDto
import br.com.androidtest.data.models.myplan.ExtraPlayOptionDto
import br.com.androidtest.data.models.myplan.HeaderItemDto
import br.com.androidtest.data.models.myplan.MyPlanResponse
import br.com.androidtest.data.models.myplan.old.OldPlansResponse
import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.models.myplan.MyPlanExtraPlay
import br.com.androidtest.domain.models.myplan.MyPlanExtraPlayOption
import br.com.androidtest.domain.models.myplan.MyPlanItemHeader

fun MyPlanResponse.toDomain() : MyPlanDomain {
    return MyPlanDomain(
        screnName = screen.name,
        offerDisplay = screen.offerDisplay,
        planValue = screen.planValue,
        status = "",
        extrasIcons = arrayListOf(),
        phoneNumber = "",
        headers = screen.header.flatten().map {
            it.toDomain()
        },
        extraPlay = screen.extraPlay.toDomain()
    )
}


fun HeaderItemDto.toDomain() : MyPlanItemHeader {
    return MyPlanItemHeader(
        title = title,
        textValue = value.text,
        alignment = value.alignment,
        textColor = value.textColor
    )
}

fun ExtraPlayDto.toDomain() : MyPlanExtraPlay {
    return MyPlanExtraPlay(
        title = title,
        options = options.map { it.toDomain() }
    )
}

fun ExtraPlayOptionDto.toDomain() : MyPlanExtraPlayOption {
    return MyPlanExtraPlayOption(
        description = description,
        url = url
    )
}


fun OldPlansResponse.toDomain() : MyPlanDomain {
    return MyPlanDomain(
        screnName =  "",
        offerDisplay = "",
        planValue = content.planValue,
        status = content.status,
        phoneNumber = content.phoneNumber,
        headers = arrayListOf(),
        extraPlay = MyPlanExtraPlay("", arrayListOf()),
        extrasIcons = content.extraPlay
            .split(";")
            .map { "${content.extraPlayBaseUrl}/$it" }
    )
}
