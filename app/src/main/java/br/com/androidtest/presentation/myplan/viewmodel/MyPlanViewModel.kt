package br.com.androidtest.presentation.myplan.viewmodel

import br.com.androidtest.presentation.myplan.actions.MyPlanActionIntent
import br.com.androidtest.presentation.myplan.event.MyPlanEvent
import br.com.androidtest.presentation.myplan.state.MyPlanState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MyPlanViewModel {
    val uiState: StateFlow<MyPlanState>
    val events: Flow<MyPlanEvent>
    fun sendActionIntent(intent: MyPlanActionIntent)
}
