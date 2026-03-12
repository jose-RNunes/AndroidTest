package br.com.androidtest.data.datasource.mydata

import br.com.androidtest.data.models.mydata.old.RWMyDataResponse

interface RWMyDataDataSource {
    suspend fun getMyData(): RWMyDataResponse
}