package br.com.androidtest.data.model

import com.google.gson.annotations.SerializedName

data class NPMyPlanResponseDto(
    @SerializedName("screen") val screen: NPMyPlanScreenDto,
)

data class NPMyPlanScreenDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("offerDisplay")
    val offerDisplay: String?,
    @SerializedName("planValue")
    val planValue: String,
    @SerializedName("header")
    val header: List<List<NPMyPlanHeaderCellDto>>,
    @SerializedName("extraPlay")
    val extraPlay: NPMyPlanExtraPlayDto,
)

data class NPMyPlanHeaderCellDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("value")
    val value: NPMyPlanHeaderValueDto,
)

data class NPMyPlanHeaderValueDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("alignment")
    val alignment: String?,
    @SerializedName("textColor")
    val textColor: String?,
)

data class NPMyPlanExtraPlayDto(
    @SerializedName("title")
    val title: String?,
    @SerializedName("options")
    val options: List<PlanAppDto>,
)

data class RWMyPlanResponseDto(
    @SerializedName("content")
    val content: RWMyPlanContentDto,
)

data class RWMyPlanContentDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("planValue")
    val planValue: String,
    @SerializedName("plan")
    val plan: Int,
    @SerializedName("bonus")
    val bonus: Int,
    @SerializedName("extraPlayBaseUrl")
    val extraPlayBaseUrl: String,
    @SerializedName("extraPlay")
    val extraPlay: String,
)

data class PlanAppDto(
    @SerializedName("description")
    val name: String,
    @SerializedName("url")
    val iconUrl: String,
)