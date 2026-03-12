package br.com.androidtest.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT : Any, STATE : Any, EVENT: Any>() : ViewModel() {

    protected abstract val initialState : STATE

    private val _uiState: MutableStateFlow<STATE> by lazy { MutableStateFlow(initialState) }
    val uiState : StateFlow<STATE> = _uiState

    private val intentChannel = Channel<INTENT>(Channel.UNLIMITED)
    val intents = intentChannel.receiveAsFlow()

    private val _events = Channel<EVENT>()
    val events = _events.receiveAsFlow()

    init {
        handleActionIntents()
    }

    fun sendActionIntent(intent: INTENT) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    fun sendEvent(event: EVENT) {
        viewModelScope.launch {
            _events.send(event)
        }
    }

    protected fun updateState(reduce: STATE.() -> STATE) {
        _uiState.value = uiState.value.reduce()
    }

    protected abstract fun handleActionIntents()
}
