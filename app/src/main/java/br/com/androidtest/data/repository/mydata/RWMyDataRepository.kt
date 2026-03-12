package br.com.androidtest.data.repository.mydata

import br.com.androidtest.data.datasource.mydata.RWMyDataDataSource
import br.com.androidtest.data.models.mydata.mapper.toDomain
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.repository.MyDataRepository

class RWMyDataRepository(
    private val rwDataSource: RWMyDataDataSource
) : MyDataRepository {
    override suspend fun getMyData(): MyDataDomain {
        val response = rwDataSource.getMyData()
        return response.toDomain()
    }
}
