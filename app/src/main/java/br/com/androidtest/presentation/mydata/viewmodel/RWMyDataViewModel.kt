package br.com.androidtest.presentation.mydata.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.androidtest.data.BaseViewModel
import br.com.androidtest.domain.models.mydata.ItemMyData
import br.com.androidtest.domain.models.mydata.MyDataAction
import br.com.androidtest.domain.models.mydata.MyDataDomain
import br.com.androidtest.domain.models.mydata.MyDataOptionDomain
import br.com.androidtest.domain.models.mock.MyDataOptionsMock
import br.com.androidtest.domain.usecase.GetMyDataUseCase
import br.com.androidtest.presentation.mydata.action.MyDataActionIntent
import br.com.androidtest.presentation.mydata.event.MyDataEvent
import br.com.androidtest.presentation.mydata.state.MyDataState
import br.com.androidtest.util.ResourceResolver
import kotlinx.coroutines.launch

class RWMyDataViewModel(
    private val getMyDataUseCase: GetMyDataUseCase,
    private val resourceResolver: ResourceResolver
)  : BaseViewModel<MyDataActionIntent, MyDataState, MyDataEvent>(), MyDataViewModel {

    override val initialState: MyDataState
        get() = MyDataState()

    override fun handleActionIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is MyDataActionIntent.Init -> getUserData()

                    is MyDataActionIntent.ShowMessageLogOut -> isShowMessageLogout()

                    is MyDataActionIntent.HideMessageLogOut -> isHideMessageLogOut()

                    is MyDataActionIntent.HandlerActionItemClicked -> handleActionItemClicked(intent.item)
                }
            }
        }
    }

    private fun handleActionItemClicked(item: ItemMyData) {
        when (item.action) {
            MyDataAction.MY_PLAN -> {
                sendEvent(MyDataEvent.GoToMyPlan)
            }
            MyDataAction.SHARE_CONTRACT -> {
                sendEvent(MyDataEvent.ShareTerms)
            }
            MyDataAction.WEB -> {
                sendEvent(MyDataEvent.OpenUrl(item.externalUrl.orEmpty()))
            }
            MyDataAction.EXIT -> {
                isShowMessageLogout()
            }

            MyDataAction.UNKNOWN -> Unit
        }
    }

    private fun getUserData() = viewModelScope.launch {
        showLoading()
        val response = getMyDataUseCase.invoke()

        updateState {
            copy(
                myData = MyDataDomain(
                    avatarUrl = response.avatarUrl,
                    name = response.name,
                    documentNumber = response.documentNumber,
                    age = response.age,
                    titleScreen = response.titleScreen
                )
            )
        }

        getOptionsAndCreateItems(response.options)
    }

    private fun getOptionsAndCreateItems(options: List<MyDataOptionDomain>) {

        val items = options.mapIndexed { _, item ->
            ItemMyData(
                icon = resourceResolver.getDrawableId(item.iconUrl ?: item.iconType),
                title = item.title,
                action = item.action,
                externalUrl = item.externalUrl
            )
        }

        updateState {
            copy(
                itemsMyData = items,
            )
        }

        hideLoading()
    }

    private fun isShowMessageLogout() {
        updateState {
            copy(
                isMessageLogOut = true
            )
        }
    }

    private fun isHideMessageLogOut() {
        updateState {
            copy(
                isMessageLogOut = false
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
