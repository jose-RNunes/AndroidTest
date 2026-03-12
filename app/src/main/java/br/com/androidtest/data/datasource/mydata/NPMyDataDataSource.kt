package br.com.androidtest.data.datasource.mydata

import br.com.androidtest.data.models.mydata.NPMyDataResponse

interface NPMyDataDataSource {
    suspend fun getMyData(): NPMyDataResponse
}