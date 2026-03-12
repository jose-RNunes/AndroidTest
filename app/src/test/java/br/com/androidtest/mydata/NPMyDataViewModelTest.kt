package br.com.androidtest.mydata

import app.cash.turbine.test
import br.com.androidtest.BaseUnitTest
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.models.mydata.MyDataOptionDomain
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.presentation.mydata.action.MyDataActionIntent
import br.com.androidtest.presentation.mydata.viewmodel.NPMyDataViewModel
import br.com.androidtest.util.ResourceResolver
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class NPMyDataViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: NPMyDataViewModel

    @RelaxedMockK
    lateinit var getMyDataUseCase: GetMyDataUseCase

    @RelaxedMockK
    lateinit var resourceResolver: ResourceResolver

    @Before
    fun setup() {
        viewModel = NPMyDataViewModel(
            getMyDataUseCase,
            resourceResolver
        )
    }

    @Test
    fun `WHEN Init intent called, MUST load user data and update state, show loading and hide loading`() = runTest {
        val response = MyDataDomain(
            avatarUrl = "url",
            name = "John",
            documentNumber = "123",
            age = "29",
            titleScreen = "My Data",
            options = emptyList()
        )

        coEvery { getMyDataUseCase.invoke() } returns response

        viewModel.uiState.test {

            viewModel.sendActionIntent(MyDataActionIntent.Init)

            val loadingState = awaitItem()
            assertTrue(loadingState.isShowLoading)

            val myDataState = awaitItem()
            assertEquals("John", myDataState.myData.name)
            assertEquals("29", myDataState.myData.age)

            val finalState = awaitItem()
            assertFalse(finalState.isShowLoading)
        }
    }

    @Test
    fun `WHEN ShowMessageLogOut intent called, MUST update state with isMessageLogOut true`() = runTest {
        viewModel.uiState.test {
            val initial = awaitItem()
            assertFalse(initial.isMessageLogOut)

            viewModel.sendActionIntent(MyDataActionIntent.ShowMessageLogOut)

            val updated = awaitItem()
            assertTrue(updated.isMessageLogOut)
        }
    }

    @Test
    fun `WHEN HideMessageLogOut intent called, MUST update state with isMessageLogOut false`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertFalse(initialState.isMessageLogOut)

            viewModel.sendActionIntent(MyDataActionIntent.ShowMessageLogOut)
            val updated = awaitItem()
            assertTrue(updated.isMessageLogOut)

            viewModel.sendActionIntent(MyDataActionIntent.HideMessageLogOut)
            val finalStateChanged = awaitItem()
            assertFalse(finalStateChanged.isMessageLogOut)
        }
    }
}