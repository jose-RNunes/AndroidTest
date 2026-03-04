package br.com.androidtest.domain.model

data class PlanHeader(
    val status: String,
    val phoneNumber: String,
    val planValue: String,
)

data class PlanQuota(
    val planGb: Int,
    val bonusGb: Int,
)

data class ExtraApp(
    val name: String,
    val iconUrl: String,
)

data class MyPlanScreen(
    val header: PlanHeader,
    val quota: PlanQuota,
    val extraApps: List<ExtraApp>,
)