package br.com.androidtest.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.androidtest.presentation.home.ui.HomeScreen
import br.com.androidtest.presentation.mydata.ui.MyDataProfileScreen
import br.com.androidtest.presentation.mydata.viewmodel.NPMyDataViewModel
import br.com.androidtest.presentation.mydata.viewmodel.RWMyDataViewModel
import br.com.androidtest.presentation.myplan.ui.MyPlanScreen
import br.com.androidtest.presentation.myplan.viewmodel.NPMyPlanViewModel
import br.com.androidtest.presentation.myplan.viewmodel.RWMyPlanViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named


@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    startDestination: String = Routes.Home.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = Routes.MyData.route,
            arguments = listOf(
                navArgument(Routes.MyData.ARG_PLATFORM) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val platform =
                backStackEntry.arguments
                    ?.getString(Routes.MyData.ARG_PLATFORM)
                    ?: "NP"

            val viewModel = when (platform) {
                "NP" -> koinViewModel<NPMyDataViewModel>(qualifier = named("NP"))
                else -> koinViewModel<RWMyDataViewModel>(qualifier = named("RW"))
            }

            MyDataProfileScreen(
                navController = navController,
                viewModel = viewModel,
                platform_prefix = platform
            )
        }

        composable(
            route = Routes.MyPlan.route,
            arguments = listOf(
                navArgument(Routes.MyPlan.ARG_PLATFORM) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val platform =
                backStackEntry.arguments
                    ?.getString(Routes.MyPlan.ARG_PLATFORM)
                    ?: "NP_plan"

            val viewModel = when (platform) {
                "NP_plan" -> koinViewModel<NPMyPlanViewModel>(qualifier = named("NP_plan"))
                else -> koinViewModel<RWMyPlanViewModel>(qualifier = named("RW_plan"))
            }

            MyPlanScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
