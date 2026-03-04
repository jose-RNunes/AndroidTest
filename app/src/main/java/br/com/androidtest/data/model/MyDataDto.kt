package br.com.androidtest.data.model

import com.google.gson.annotations.SerializedName

data class NPMyDataResponseDto(
    @SerializedName("screen") val screen: NPMyDataScreenDto,
)

data class NPMyDataScreenDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("profile")
    val profile: UserDto,
    @SerializedName("options")
    val options: List<MyDataOptionDto>,
)

data class RWMyDataResponseDto(
    @SerializedName("content")
    val content: RWMyDataContentDto,
)

data class RWMyDataContentDto(
    @SerializedName("user")
    val user: UserDto,
)

data class UserDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("documentNumber")
    val documentNumber: String,
    @SerializedName("avatarUrl")
    val avatarUrl: String,
)

data class MyDataOptionDto(
    @SerializedName("action")
    val action: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("iconUrl")
    val iconUrl: String?,
    @SerializedName("iconType")
    val iconType: String?,
    @SerializedName("assetName")
    val assetName: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("modal")
    val modal: ExitModalDto?,
)

data class ExitModalDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("buttons")
    val buttons: List<ModalButtonDto>,
)

data class ModalButtonDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("action")
    val action: String,
)