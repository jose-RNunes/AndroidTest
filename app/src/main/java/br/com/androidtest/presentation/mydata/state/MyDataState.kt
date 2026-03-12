package br.com.androidtest.presentation.mydata.state

import br.com.androidtest.domain.models.mydata.ItemMyData
import br.com.androidtest.domain.models.mydata.MyDataDomain

data class MyDataState (
    var isShowLoading: Boolean = true,
    var isMessageLogOut: Boolean = false,
    var myData: MyDataDomain = MyDataDomain(),
    var itemsMyData: List<ItemMyData> = arrayListOf(),
)
