package br.com.androidtest.presentation.mydata.action

import br.com.androidtest.domain.models.mydata.ItemMyData

sealed class MyDataActionIntent {
    object Init : MyDataActionIntent()
    object ShowMessageLogOut : MyDataActionIntent()
    object HideMessageLogOut : MyDataActionIntent()
    data class HandlerActionItemClicked (val item: ItemMyData) : MyDataActionIntent()
}
