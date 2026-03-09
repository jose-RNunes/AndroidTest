package br.com.androidtest.features.myplan.viewmodel

import br.com.androidtest.features.myplan.model.MyPlanScreenState
import kotlinx.coroutines.flow.StateFlow

interface MyPlanViewModelContract {
    val state: StateFlow<MyPlanScreenState>
    fun load(forceRefresh: Boolean)
}
