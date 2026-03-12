package br.com.androidtest.data.models.myplan

data class MyPlanScreenDto(
    val name: String,
    val offerDisplay: String,
    val planValue: String,
    val header: List<List<HeaderItemDto>>,
    val extraPlay: ExtraPlayDto
)
