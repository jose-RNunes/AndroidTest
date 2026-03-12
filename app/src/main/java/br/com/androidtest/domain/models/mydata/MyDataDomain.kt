package br.com.androidtest.domain.models.mydata

data class MyDataDomain(
    val name: String = "",
    val age: String = "",
    val documentNumber: String = "",
    val avatarUrl: String = "",
    var options: List<MyDataOptionDomain> = arrayListOf(),
    val titleScreen: String = ""
)
