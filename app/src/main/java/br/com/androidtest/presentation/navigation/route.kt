package br.com.androidtest.presentation.navigation

object Route {
    const val HOME = "home"
    const val MY_DATA = "my_data/{platform}"
    const val MY_PLAN = "my_plan/{platform}"

    fun myData(platform: String) = "my_data/$platform"
    fun myPlan(platform: String) = "my_plan/$platform"
}