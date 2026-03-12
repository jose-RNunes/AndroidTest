package br.com.androidtest.domain.usecase

import br.com.androidtest.domain.models.mock.MyDataOptionsMock
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.repository.MyDataRepository

class GetMyDataUseCase (
    private val myDataRepository: MyDataRepository
) {
    suspend fun invoke(): MyDataDomain {
        return myDataRepository.getMyData().apply {
            options = MyDataOptionsMock.getOptions()
        }
    }
}
