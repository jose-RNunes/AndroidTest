package br.com.androidtest.domain.models.mydata

data class MyDataOptionDomain(
    val iconUrl: String? = null,
    val iconType: String? = null,
    val title: String,
    val externalUrl: String? = null,
    val action: MyDataAction,
)
