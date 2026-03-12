package br.com.androidtest.presentation.myplan.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.androidtest.core.getDescriptionIconFromUrl
import br.com.androidtest.data.BaseViewModel
import br.com.androidtest.domain.models.mock.ItemsHeaderMock
import br.com.androidtest.domain.models.mock.MyPlanDomainMock
import br.com.androidtest.domain.models.myplan.ItemExtraPlan
import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.models.myplan.MyPlanItemHeader
import br.com.androidtest.domain.usecase.GetMyPlanUseCase
import br.com.androidtest.presentation.myplan.actions.MyPlanActionIntent
import br.com.androidtest.presentation.myplan.event.MyPlanEvent
import br.com.androidtest.presentation.myplan.state.MyPlanState
import kotlinx.coroutines.launch

class RWMyPlanViewModel(
    private val getMyPlanUseCase: GetMyPlanUseCase
) : BaseViewModel<MyPlanActionIntent, MyPlanState, MyPlanEvent>(), MyPlanViewModel  {

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
        configureExtrasPlansIcons(response.extrasIcons)
        configureListHeaders(ItemsHeaderMock.itemsHeader)
        configureOtherLabels(response.phoneNumber, response.planValue, response.status)
        hideLoading()
    }

    private fun configureListHeaders(headers: List<MyPlanItemHeader>) {
        updateState {
            copy(
                itemsHeader = headers
            )
        }
    }

    private fun configureExtrasPlansIcons(options: List<String>) {
        val list = options.map {
            ItemExtraPlan(
                description = it.getDescriptionIconFromUrl(),
                url = it
            )
        }
        updateState {
            copy(
                iconsExtrasPlan = list
            )
        }
    }

    private fun configureOtherLabels(phoneNumber: String, planValue: String, status: String) {
        updateState {
            copy(
                myPlanDomain= MyPlanDomain(
                    screnName = MyPlanDomainMock.getMyPlanMock().screnName,
                    offerDisplay =  MyPlanDomainMock.getMyPlanMock().offerDisplay,
                    planValue =  planValue,
                    phoneNumber = phoneNumber,
                    extraPlay = MyPlanDomainMock.getMyPlanMock().extraPlay,
                    status = status,
                    headers = MyPlanDomainMock.getMyPlanMock().headers,
                )
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