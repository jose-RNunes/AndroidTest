package br.com.androidtest.viewmodel

import br.com.androidtest.data.repository.MyPlanRepository
import br.com.androidtest.domain.model.MyPlanScreen
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.factory.DataFactory
import br.com.androidtest.presentation.UiState
import br.com.androidtest.presentation.feature.myplan.NPMyPlanViewModel
import br.com.androidtest.presentation.feature.myplan.RWMyPlanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MyPlanViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val fakeUseCase = GetMyPlanUseCase(object : MyPlanRepository {
        override suspend fun getMyPlan(): MyPlanScreen = DataFactory.myPlanScreen
    })

    private val failingUseCase = GetMyPlanUseCase(object : MyPlanRepository {
        override suspend fun getMyPlan(): MyPlanScreen = throw RuntimeException("Erro simulado")
    })

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `NPMyPlanViewModel load deve emitir Loading e depois Success`() = runTest {
        val viewModel = NPMyPlanViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Success)
    }

    @Test
    fun `NPMyPlanViewModel load Success deve conter dados corretos`() = runTest {
        val viewModel = NPMyPlanViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        assertEquals("Ativo", uiModel.status)
        assertEquals("(11) 99999-9999", uiModel.phoneNumber)
        assertEquals(15, uiModel.planGb)
        assertEquals(5, uiModel.bonusGb)
        assertEquals(1, uiModel.apps.size)
    }

    @Test
    fun `NPMyPlanViewModel load deve emitir Error quando useCase falhar`() = runTest {
        val viewModel = NPMyPlanViewModel(failingUseCase)

        viewModel.load()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertEquals("Erro simulado", (viewModel.uiState.value as UiState.Error).message)
    }

    @Test
    fun `RWMyPlanViewModel load deve emitir Success com dados do dominio`() = runTest {
        val viewModel = RWMyPlanViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        assertEquals("Ativo", uiModel.status)
        assertEquals(15, uiModel.planGb)
        assertEquals(5, uiModel.bonusGb)
    }

    @Test
    fun `RWMyPlanViewModel load deve emitir Error quando useCase falhar`() = runTest {
        val viewModel = RWMyPlanViewModel(failingUseCase)

        viewModel.load()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Error)
    }

    @Test
    fun `RWMyPlanViewModel planGb mais bonusGb deve representar total de GB`() = runTest {
        val viewModel = RWMyPlanViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        assertEquals(20, uiModel.planGb + uiModel.bonusGb)
    }
}

