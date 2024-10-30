package com.example.kinoteka.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    @SerialName("nickName")
    val nickName: String = "",
    @SerialName("name")
    val name: String = "Вася",
    @SerialName("avatarLink")
    val avatarLink: String?,
    @SerialName("email")
    val email: String = "user@example.com",
    @SerialName("birthDate")
    val birthDate: String = "2024-10-30T07:24:56.845Z",
    @SerialName("gender")
    val gender: Int = 0
)
