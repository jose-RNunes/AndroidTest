package br.com.androidtest.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object MyData {
        const val route = "my_data/{typePlatform}"
        const val ARG_PLATFORM = "typePlatform"

        fun createRoute(platform: String) =
            "my_data/$platform"
    }
    object MyPlan {
        const val route = "my_plan/{typePlatformPlan}"
        const val ARG_PLATFORM = "typePlatformPlan"

        fun createRoute(platform: String) =
            "my_plan/$platform"
    }
}
