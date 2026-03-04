package br.com.androidtest.usecase

import br.com.androidtest.data.repository.MyDataRepository
import br.com.androidtest.domain.model.MyDataScreen
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.factory.DataFactory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMyDataUseCaseTest {

    private val fakeRepository = object : MyDataRepository {
        override suspend fun getMyData(): MyDataScreen = DataFactory.myDataScreen
    }

    private val useCase = GetMyDataUseCase(fakeRepository)

    @Test
    fun `execute deve retornar os dados vindos do repositorio`() = runTest {
        val result = useCase.execute()

        assertEquals(DataFactory.myDataScreen, result)
    }

    @Test
    fun `execute deve propagar excecao quando repositorio falhar`() = runTest {
        val errorRepository = object : MyDataRepository {
            override suspend fun getMyData(): MyDataScreen = throw RuntimeException("Falha no repositorio")
        }
        val useCaseWithError = GetMyDataUseCase(errorRepository)

        var caughtException: Exception? = null
        try {
            useCaseWithError.execute()
        } catch (e: RuntimeException) {
            caughtException = e
        }

        assertEquals("Falha no repositorio", caughtException?.message)
    }
}


