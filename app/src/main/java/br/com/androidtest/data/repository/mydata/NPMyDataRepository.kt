package br.com.androidtest.data.repository.mydata

import br.com.androidtest.data.datasource.mydata.NPMyDataDataSource
import br.com.androidtest.data.models.mydata.mapper.toDomain
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.repository.MyDataRepository

class NPMyDataRepository(
    private val dataSource: NPMyDataDataSource
) : MyDataRepository {
    override suspend fun getMyData(): MyDataDomain {
        val response = dataSource.getMyData()
        return response.toDomain()
    }
}
