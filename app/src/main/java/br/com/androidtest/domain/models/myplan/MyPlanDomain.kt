package br.com.androidtest.domain.models.myplan

data class MyPlanDomain (
    val screnName: String,
    val offerDisplay: String,
    val planValue: String,
    val status: String,
    val headers: List<MyPlanItemHeader> = arrayListOf(),
    val extraPlay: MyPlanExtraPlay = MyPlanExtraPlay("", arrayListOf()),
    val extrasIcons: List<String> = arrayListOf(),
    val phoneNumber: String,
)
