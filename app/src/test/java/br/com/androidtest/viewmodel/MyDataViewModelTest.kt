package br.com.androidtest.viewmodel

import br.com.androidtest.data.repository.MyDataRepository
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.factory.DataFactory
import br.com.androidtest.presentation.UiState
import br.com.androidtest.presentation.feature.mydata.MyDataEffect
import br.com.androidtest.presentation.feature.mydata.MyDataUiActionType
import br.com.androidtest.presentation.feature.mydata.MyDataActionUiModel
import br.com.androidtest.presentation.feature.mydata.NPMyDataViewModel
import br.com.androidtest.presentation.feature.mydata.RWMyDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

class MyDataViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val fakeUseCase = GetMyDataUseCase(object : MyDataRepository {
        override suspend fun getMyData(): MyDataScreen = DataFactory.myDataScreen
    })

    private val failingUseCase = GetMyDataUseCase(object : MyDataRepository {
        override suspend fun getMyData(): MyDataScreen = throw RuntimeException("Erro simulado")
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
    fun `NPMyDataViewModel load deve emitir Loading e depois Success`() = runTest {
        val viewModel = NPMyDataViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
    }

    @Test
    fun `NPMyDataViewModel load Success deve conter dados corretos`() = runTest {
        val viewModel = NPMyDataViewModel(fakeUseCase)

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        assertEquals("Meus dados", uiModel.title)
        assertEquals("Ze", uiModel.userName)
        assertEquals(2, uiModel.actions.size)
    }

    @Test
    fun `NPMyDataViewModel load deve emitir Error quando useCase falhar`() = runTest {
        val viewModel = NPMyDataViewModel(failingUseCase)

        viewModel.load()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertEquals("Erro simulado", (viewModel.uiState.value as UiState.Error).message)
    }

    @Test
    fun `NPMyDataViewModel onActionClicked DOWNLOAD_CONTRACT deve emitir SharePdf`() = runTest {
        val viewModel = NPMyDataViewModel(fakeUseCase)
        viewModel.load()
        advanceUntilIdle()

        val collectedEffects = mutableListOf<MyDataEffect>()
        val job = launch { viewModel.effects.collect { collectedEffects.add(it) } }

        viewModel.onActionClicked(
            MyDataActionUiModel(
                id = "DOWNLOAD_CONTRACT",
                title = "Baixar contrato",
                iconKey = "ic_download",
                type = MyDataUiActionType.DOWNLOAD_CONTRACT,
                assetName = "terms.pdf",
                url = null,
                exitDialog = null,
            ),
        )
        advanceUntilIdle()
        job.cancel()

        assertEquals(1, collectedEffects.size)
        assertTrue(collectedEffects.first() is MyDataEffect.SharePdf)
        assertEquals("terms.pdf", (collectedEffects.first() as MyDataEffect.SharePdf).assetName)
    }

    @Test
    fun `NPMyDataViewModel onActionClicked PRIVACY deve emitir OpenUrl`() = runTest {
        val viewModel = NPMyDataViewModel(fakeUseCase)
        viewModel.load()
        advanceUntilIdle()

        val collectedEffects = mutableListOf<MyDataEffect>()
        val job = launch { viewModel.effects.collect { collectedEffects.add(it) } }

        viewModel.onActionClicked(
            MyDataActionUiModel(
                id = "PRIVACY",
                title = "Privacidade",
                iconKey = "ic_message",
                type = MyDataUiActionType.PRIVACY,
                assetName = null,
                url = "https://privacy.url",
                exitDialog = null,
            ),
        )
        advanceUntilIdle()
        job.cancel()

        assertEquals(1, collectedEffects.size)
        assertTrue(collectedEffects.first() is MyDataEffect.OpenUrl)
        assertEquals("https://privacy.url", (collectedEffects.first() as MyDataEffect.OpenUrl).url)
    }

    @Test
    fun `RWMyDataViewModel load deve emitir Success com privacyUrl injetada`() = runTest {
        val privacyUrl = "https://privacidade.claro.com.br"
        val viewModel = RWMyDataViewModel(fakeUseCase, privacyUrl)

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        val privacyAction = uiModel.actions.first { it.type == MyDataUiActionType.PRIVACY }
        assertEquals(privacyUrl, privacyAction.url)
    }

    @Test
    fun `RWMyDataViewModel load deve conter 4 actions fixas`() = runTest {
        val viewModel = RWMyDataViewModel(fakeUseCase, "https://privacy.url")

        viewModel.load()
        advanceUntilIdle()

        val uiModel = (viewModel.uiState.value as UiState.Success).data
        assertEquals(4, uiModel.actions.size)
    }

    @Test
    fun `RWMyDataViewModel load deve emitir Error quando useCase falhar`() = runTest {
        val viewModel = RWMyDataViewModel(failingUseCase, "https://privacy.url")

        viewModel.load()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}

