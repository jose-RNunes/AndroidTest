package br.com.androidtest.presentation.myplan.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.androidtest.data.BaseViewModel
import br.com.androidtest.domain.models.myplan.ItemExtraPlan
import br.com.androidtest.domain.models.myplan.MyPlanExtraPlayOption
import br.com.androidtest.domain.models.myplan.MyPlanItemHeader
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.myplan.actions.MyPlanActionIntent
import br.com.androidtest.presentation.myplan.event.MyPlanEvent
import br.com.androidtest.presentation.myplan.state.MyPlanState
import kotlinx.coroutines.launch

class NPMyPlanViewModel (
    private val getMyPlanUseCase: GetMyPlanUseCase
) : BaseViewModel<MyPlanActionIntent, MyPlanState, MyPlanEvent>(), MyPlanViewModel {

    override val initialState: MyPlanState
        get() = MyPlanState()

    override fun handleActionIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is MyPlanActionIntent.Init -> getMyPlan()
                }
            }
        }
    }

    private fun getMyPlan() = viewModelScope.launch {
        showLoading()
        val response = getMyPlanUseCase.invoke()
        updateState {
            copy(
                myPlanDomain = response
            )
        }
        configureExtrasPlansIcons(response.extraPlay.options)
        configureListHeaders(response.headers)
        hideLoading()
    }

    private fun configureListHeaders(headers: List<MyPlanItemHeader>) {
        updateState {
            copy(
                itemsHeader = headers
            )
        }
    }

    private fun configureExtrasPlansIcons(options: List<MyPlanExtraPlayOption>) {
        val list = options.map {
            ItemExtraPlan(
                description = it.description,
                url = it.url
            )
        }
        updateState {
            copy(
                iconsExtrasPlan = list
            )
        }
    }

    private fun showLoading() {
        updateState {
            copy(
                isShowLoading = true
            )
        }
    }

    private fun hideLoading() {
        updateState {
            copy(
                isShowLoading = false
            )
        }
    }
}
