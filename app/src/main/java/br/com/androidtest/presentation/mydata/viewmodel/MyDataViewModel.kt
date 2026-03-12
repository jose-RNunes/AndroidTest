package br.com.androidtest.presentation.mydata.viewmodel

import br.com.androidtest.presentation.mydata.action.MyDataActionIntent
import br.com.androidtest.presentation.mydata.event.MyDataEvent
import br.com.androidtest.presentation.mydata.state.MyDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MyDataViewModel {
    val uiState: StateFlow<MyDataState>

    val events: Flow<MyDataEvent>
    fun sendActionIntent(intent: MyDataActionIntent)
}
