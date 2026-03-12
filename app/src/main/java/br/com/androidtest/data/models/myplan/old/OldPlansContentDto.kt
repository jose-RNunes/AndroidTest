package br.com.androidtest.data.models.myplan.old

data class OldPlansContentDto(
    val planValue: String,
    val status: String,
    val phoneNumber: String,
    val plan: Int?,
    val bonus: Int?,
    val extraPlayBaseUrl: String,
    val extraPlay: String
)
