package br.com.androidtest.features.mydata.viewmodel

import br.com.androidtest.features.mydata.model.MyDataScreenState
import kotlinx.coroutines.flow.StateFlow

interface MyDataViewModelContract {
    val state: StateFlow<MyDataScreenState>
    fun load(forceRefresh: Boolean)
}
