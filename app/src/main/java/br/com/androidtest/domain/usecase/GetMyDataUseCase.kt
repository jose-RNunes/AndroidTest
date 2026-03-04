package br.com.androidtest.domain.usecase

import br.com.androidtest.data.repository.MyDataRepository
import br.com.androidtest.domain.model.MyDataScreen

class GetMyDataUseCase(
    private val repository: MyDataRepository,
) {
    suspend fun execute(): MyDataScreen = repository.getMyData()
}