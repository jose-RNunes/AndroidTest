package br.com.androidtest.myplan

import br.com.androidtest.BaseUnitTest
import br.com.androidtest.domain.models.mock.ItemsHeaderMock
import br.com.androidtest.domain.models.mock.MyPlanDomainMock
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.myplan.actions.MyPlanActionIntent
import br.com.androidtest.presentation.myplan.viewmodel.NPMyPlanViewModel
import br.com.androidtest.presentation.myplan.viewmodel.RWMyPlanViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse


class RWMyPlanViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: RWMyPlanViewModel

    @RelaxedMockK
    lateinit var getMyPlanUseCase: GetMyPlanUseCase

    @Before
    fun setup() {
        viewModel = RWMyPlanViewModel(
            getMyPlanUseCase
        )
    }

    @Test
    fun `WHEN call Init MUST update ui state`() = runTest {

        val response = MyPlanDomainMock.getMyPlanMock()
        coEvery { getMyPlanUseCase.invoke() } returns response

        val itemsHeaderMock = ItemsHeaderMock.itemsHeader

        viewModel.sendActionIntent(MyPlanActionIntent.Init)

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertEquals("Plano Claro Flex", state.myPlanDomain.screnName)
        assertEquals("20GB", state.myPlanDomain.offerDisplay)
        assertEquals(0, state.iconsExtrasPlan.size)
        assertEquals(4, itemsHeaderMock.size)
        assertFalse(state.isShowLoading)
    }
}
