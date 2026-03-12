package br.com.androidtest.data.models.mydata

data class ScreenDto(
    val title: String,
    val profile: ProfileDto,
    val options: List<OptionDto>
)
