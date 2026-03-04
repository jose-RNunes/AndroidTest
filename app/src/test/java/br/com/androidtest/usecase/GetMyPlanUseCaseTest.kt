package br.com.androidtest.usecase

import br.com.androidtest.data.repository.MyPlanRepository
import br.com.androidtest.domain.model.MyPlanScreen
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.factory.DataFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMyPlanUseCaseTest {

    private val fakeRepository = object : MyPlanRepository {
        override suspend fun getMyPlan(): MyPlanScreen = DataFactory.myPlanScreen
    }

    private val useCase = GetMyPlanUseCase(fakeRepository)

    @Test
    fun `execute deve retornar os dados vindos do repositorio`() = runTest {
        val result = useCase.execute()

        assertEquals(DataFactory.myPlanScreen, result)
    }

    @Test
    fun `execute deve propagar excecao quando repositorio falhar`() = runTest {
        val errorRepository = object : MyPlanRepository {
            override suspend fun getMyPlan(): MyPlanScreen = throw RuntimeException("Falha no repositorio")
        }
        val useCaseWithError = GetMyPlanUseCase(errorRepository)

        var caughtException: Exception? = null
        try {
            useCaseWithError.execute()
        } catch (e: RuntimeException) {
            caughtException = e
        }

        assertEquals("Falha no repositorio", caughtException?.message)
    }
}


