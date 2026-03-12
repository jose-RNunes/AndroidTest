package br.com.androidtest.presentation.home.state

import br.com.androidtest.domain.models.ItemHome

data class HomeState (
    var isMessageLogOut: Boolean = false,
    var listItemsHome: List<ItemHome> = arrayListOf()
)
