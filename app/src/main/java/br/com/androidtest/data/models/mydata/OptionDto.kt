package br.com.androidtest.data.models.mydata

data class OptionDto(
    val iconUrl: String? = null,
    val iconType: String? = null,
    val title: String,
    val action: String,
    val assetName: String? = null,
    val url: String? = null,
    val modal: ModalDto? = null
)
