package br.com.androidtest.presentation.home.actions

sealed class HomeActionIntent {
    object Init : HomeActionIntent()
    object ShowMessageLogOut : HomeActionIntent()
    object HideMessageLogOut : HomeActionIntent()
}
