package br.com.androidtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.androidtest.di.npQualifier
import br.com.androidtest.di.rwQualifier
import br.com.androidtest.presentation.feature.home.HomeScreen
import br.com.androidtest.presentation.feature.mydata.MyDataScreen
import br.com.androidtest.presentation.feature.mydata.MyDataViewModelContract
import br.com.androidtest.presentation.feature.mydata.NPMyDataViewModel
import br.com.androidtest.presentation.feature.mydata.RWMyDataViewModel
import br.com.androidtest.presentation.feature.myplan.MyPlanScreen
import br.com.androidtest.presentation.feature.myplan.MyPlanViewModelContract
import br.com.androidtest.presentation.feature.myplan.NPMyPlanViewModel
import br.com.androidtest.presentation.feature.myplan.RWMyPlanViewModel
import br.com.androidtest.utils.Platform
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun AppNavGraph(
    onExitApp: () -> Unit,
) {
    val navController = rememberNavController()
    val currentExit by rememberUpdatedState(onExitApp)

    NavHost(
        navController = navController,
        startDestination = Route.HOME,
    ) {
        composable(Route.HOME) {
            HomeScreen(
                onPlatformSelected = { platform ->
                    navController.navigate(Route.myData(platform.code))
                },
                onExitConfirmed = currentExit,
            )
        }

        composable(
            route = Route.MY_DATA,
            arguments = listOf(navArgument("platform") { type = NavType.StringType }),
        ) { entry ->
            val platform = Platform.fromCode(entry.arguments?.getString("platform"))
            val viewModel: MyDataViewModelContract = when (platform) {
                Platform.NP -> koinViewModel<NPMyDataViewModel>(qualifier = npQualifier)
                Platform.RW -> koinViewModel<RWMyDataViewModel>(qualifier = rwQualifier)
            }

            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(viewModel) { viewModel.load() }

            MyDataScreen(
                uiState = uiState,
                effects = viewModel.effects,
                onActionClick = viewModel::onActionClicked,
                onGoToMyPlan = { navController.navigate(Route.myPlan(platform.code)) },
                onBackToHome = { navController.popBackStack() },
                onExitApp = currentExit,
            )
        }

        composable(
            route = Route.MY_PLAN,
            arguments = listOf(navArgument("platform") { type = NavType.StringType }),
        ) { entry ->
            val platform = Platform.fromCode(entry.arguments?.getString("platform"))
            val viewModel: MyPlanViewModelContract = when (platform) {
                Platform.NP -> koinViewModel<NPMyPlanViewModel>(qualifier = named(Platform.NP.code))
                Platform.RW -> koinViewModel<RWMyPlanViewModel>(qualifier = named(Platform.RW.code))
            }

            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(viewModel) { viewModel.load() }

            MyPlanScreen(
                uiState = uiState,
                onBack = { navController.popBackStack() },
            )
        }
    }
}