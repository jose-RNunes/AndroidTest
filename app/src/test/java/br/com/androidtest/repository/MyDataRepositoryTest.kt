package br.com.androidtest.repository

import br.com.androidtest.data.model.NPMyDataResponseDto
import br.com.androidtest.data.model.NPMyPlanResponseDto
import br.com.androidtest.data.model.RWMyDataResponseDto
import br.com.androidtest.data.model.RWMyPlanResponseDto
import br.com.androidtest.data.repository.NpMyDataRepository
import br.com.androidtest.data.repository.RwMyDataRepository
import br.com.androidtest.data.service.ApiService
import br.com.androidtest.domain.model.MyDataActionType
import br.com.androidtest.factory.DataFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MyDataRepositoryTest {

    private val fakeApi = object : ApiService {
        override suspend fun getMyDataNP(): NPMyDataResponseDto = DataFactory.npMyDataResponseDto
        override suspend fun getMyDataRW(): RWMyDataResponseDto = DataFactory.rwMyDataResponseDto
        override suspend fun getMyPlanNP(): NPMyPlanResponseDto = DataFactory.npMyPlanResponseDto
        override suspend fun getMyPlanRW(): RWMyPlanResponseDto = DataFactory.rwMyPlanResponseDto
    }

    @Test
    fun `NpMyDataRepository deve mapear DTO NP para MyDataScreen corretamente`() = runTest {
        val repository = NpMyDataRepository(fakeApi)

        val result = repository.getMyData()

        assertEquals("Meus dados", result.title)
        assertEquals("Ze", result.user.name)
        assertEquals("000.000.000-00", result.user.documentNumber)
        assertEquals(2, result.actions.size)
        assertEquals(MyDataActionType.MY_PLAN, result.actions[0].type)
        assertEquals(MyDataActionType.EXIT, result.actions[1].type)
    }

    @Test
    fun `NpMyDataRepository deve mapear exitDialog quando modal presente`() = runTest {
        val repository = NpMyDataRepository(fakeApi)

        val result = repository.getMyData()

        val exitAction = result.actions.first { it.type == MyDataActionType.EXIT }
        assertEquals("Sair?", exitAction.exitDialogConfig?.title)
        assertEquals("Sim", exitAction.exitDialogConfig?.confirmLabel)
        assertEquals("Nao", exitAction.exitDialogConfig?.cancelLabel)
    }

    @Test
    fun `RwMyDataRepository deve mapear DTO RW para MyDataScreen com actions vazias`() = runTest {
        val repository = RwMyDataRepository(fakeApi)

        val result = repository.getMyData()

        assertEquals("Meus dados", result.title)
        assertEquals("Ze", result.user.name)
        assertTrue(result.actions.isEmpty())
    }
}

