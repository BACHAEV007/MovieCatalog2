package com.example.kinoteka.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    @SerialName("nickName")
    val nickName: String,
    @SerialName("name")
    val name: String,
    @SerialName("avatarLink")
    val avatarLink: String,
    @SerialName("email")
    val email: String,
    @SerialName("birthDate")
    val birthDate: String = "",
    @SerialName("gender")
    val gender: Int = 0
)
