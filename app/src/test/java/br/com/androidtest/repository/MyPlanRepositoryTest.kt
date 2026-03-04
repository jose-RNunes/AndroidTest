package br.com.androidtest.repository

import br.com.androidtest.data.model.NPMyDataResponseDto
import br.com.androidtest.data.model.NPMyPlanResponseDto
import br.com.androidtest.data.model.RWMyDataResponseDto
import br.com.androidtest.data.model.RWMyPlanResponseDto
import br.com.androidtest.data.repository.NpMyPlanRepository
import br.com.androidtest.data.repository.RwMyPlanRepository
import br.com.androidtest.data.service.ApiService
import br.com.androidtest.factory.DataFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MyPlanRepositoryTest {

    private val fakeApi = object : ApiService {
        override suspend fun getMyDataNP(): NPMyDataResponseDto = DataFactory.npMyDataResponseDto
        override suspend fun getMyDataRW(): RWMyDataResponseDto = DataFactory.rwMyDataResponseDto
        override suspend fun getMyPlanNP(): NPMyPlanResponseDto = DataFactory.npMyPlanResponseDto
        override suspend fun getMyPlanRW(): RWMyPlanResponseDto = DataFactory.rwMyPlanResponseDto
    }

    @Test
    fun `NpMyPlanRepository deve mapear DTO NP para MyPlanScreen corretamente`() = runTest {
        val repository = NpMyPlanRepository(fakeApi)

        val result = repository.getMyPlan()

        assertEquals("Ativo", result.header.status)
        assertEquals("(11) 99999-9999", result.header.phoneNumber)
        assertEquals("R$ 39,99", result.header.planValue)
        assertEquals(15, result.quota.planGb)
        assertEquals(5, result.quota.bonusGb)
        assertEquals(1, result.extraApps.size)
        assertEquals("youtube", result.extraApps.first().name)
    }

    @Test
    fun `RwMyPlanRepository deve mapear DTO RW para MyPlanScreen corretamente`() = runTest {
        val repository = RwMyPlanRepository(fakeApi)

        val result = repository.getMyPlan()

        assertEquals("Ativo", result.header.status)
        assertEquals(15, result.quota.planGb)
        assertEquals(5, result.quota.bonusGb)
        assertEquals("youtube", result.extraApps.first().name)
        assertEquals("https://icons.url/youtube.png", result.extraApps.first().iconUrl)
    }

    @Test
    fun `RwMyPlanRepository deve montar iconUrl combinando baseUrl com fileName`() = runTest {
        val repository = RwMyPlanRepository(fakeApi)

        val result = repository.getMyPlan()

        assertTrue(result.extraApps.first().iconUrl.startsWith("https://icons.url"))
    }
}



