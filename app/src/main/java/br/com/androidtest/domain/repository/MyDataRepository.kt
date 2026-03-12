package br.com.androidtest.domain.repository

import br.com.androidtest.domain.models.mydata.MyDataDomain

interface MyDataRepository {
    suspend fun getMyData(): MyDataDomain
}
