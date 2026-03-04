package br.com.androidtest.data.mapper

import br.com.androidtest.data.model.NPMyPlanResponseDto
import br.com.androidtest.data.model.RWMyPlanResponseDto
import br.com.androidtest.domain.model.ExtraApp
import br.com.androidtest.domain.model.MyPlanScreen
import br.com.androidtest.domain.model.PlanHeader
import br.com.androidtest.domain.model.PlanQuota

fun NPMyPlanResponseDto.toDomainModel(): MyPlanScreen {
    val firstRow = screen.header[0]
    val secondRow = screen.header[1]

    val status = firstRow[0].value.text
    val phone = firstRow[1].value.text
    val planText = secondRow[0].value.text
    val bonusText = secondRow[1].value.text

    return MyPlanScreen(
        header = PlanHeader(
            status = status,
            phoneNumber = phone,
            planValue = screen.planValue,
        ),
        quota = PlanQuota(
            planGb = planText.filter { it.isDigit() }.toIntOrNull() ?: 0,
            bonusGb = bonusText.filter { it.isDigit() }.toIntOrNull() ?: 0,
        ),
        extraApps = screen.extraPlay.options.map { ExtraApp(name = it.name, iconUrl = it.iconUrl) },
    )
}

fun RWMyPlanResponseDto.toDomainModel(): MyPlanScreen {
    val baseUrl = content.extraPlayBaseUrl.trimEnd('/')
    return MyPlanScreen(
        header = PlanHeader(
            status = content.status,
            phoneNumber = content.phoneNumber,
            planValue = content.planValue,
        ),
        quota = PlanQuota(
            planGb = content.plan,
            bonusGb = content.bonus,
        ),
        extraApps = content.extraPlay
            .split(";")
            .filter { it.isNotBlank() }
            .map { fileName ->
                ExtraApp(
                    name = fileName.substringBefore("."),
                    iconUrl = "$baseUrl/$fileName",
                )
            },
    )
}