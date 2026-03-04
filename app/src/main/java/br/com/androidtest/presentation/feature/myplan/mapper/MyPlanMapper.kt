package br.com.androidtest.presentation.feature.myplan.mapper

import br.com.androidtest.domain.model.MyPlanScreen
import br.com.androidtest.presentation.feature.myplan.MyPlanUiModel
import br.com.androidtest.presentation.feature.myplan.PlanAppUiModel

fun MyPlanScreen.toUiModel(): MyPlanUiModel {
    return MyPlanUiModel(
        status = header.status,
        phoneNumber = header.phoneNumber,
        planValue = header.planValue,
        planGb = quota.planGb,
        bonusGb = quota.bonusGb,
        apps = extraApps.map { PlanAppUiModel(name = it.name, iconUrl = it.iconUrl) },
    )
}