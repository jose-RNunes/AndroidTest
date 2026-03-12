package br.com.androidtest.presentation.mydata.event

sealed class MyDataEvent {
    data object ShareTerms : MyDataEvent()
    data class OpenUrl(val url: String) : MyDataEvent()

    data object GoToMyPlan : MyDataEvent()
}
