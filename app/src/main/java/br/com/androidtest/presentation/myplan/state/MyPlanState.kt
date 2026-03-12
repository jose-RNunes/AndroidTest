package br.com.androidtest.presentation.myplan.state

import br.com.androidtest.domain.models.myplan.ItemExtraPlan
import br.com.androidtest.domain.models.myplan.MyPlanDomain
import br.com.androidtest.domain.models.myplan.MyPlanExtraPlay
import br.com.androidtest.domain.models.myplan.MyPlanItemHeader

data class MyPlanState(
    var isShowLoading: Boolean = true,
    var myPlanDomain: MyPlanDomain = MyPlanDomain(
        screnName = "",
        offerDisplay = "",
        planValue = "",
        status = "",
        headers = arrayListOf(),
        extraPlay = MyPlanExtraPlay("", arrayListOf()),
        extrasIcons = arrayListOf(),
        phoneNumber = "",
    ),
    val iconsExtrasPlan : List<ItemExtraPlan> = arrayListOf(),
    val itemsHeader: List<MyPlanItemHeader> = arrayListOf()
)
