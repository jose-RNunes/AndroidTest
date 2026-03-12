package br.com.androidtest.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.com.androidtest.data.BaseViewModel
import br.com.androidtest.domain.models.ItemHome
import br.com.androidtest.presentation.home.actions.HomeActionIntent
import br.com.androidtest.presentation.home.state.HomeState
import kotlinx.coroutines.launch

object NoEvent

class HomeViewModel : BaseViewModel<HomeActionIntent, HomeState, NoEvent>()  {

    override val initialState: HomeState
        get() = HomeState()

    override fun handleActionIntents() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    HomeActionIntent.Init -> {
                        getItemsHome()
                    }

                    HomeActionIntent.ShowMessageLogOut -> isShowMessageLogout()

                    HomeActionIntent.HideMessageLogOut -> isHideMessageLogOut()

                }
            }
        }
    }

    private fun getItemsHome() {
        updateState {
            copy(
                listItemsHome = listOf(
                    ItemHome(1, "Plataforma Nova"),
                    ItemHome(2, "Plataforma Antiga"),
                )
            )
        }
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
}
