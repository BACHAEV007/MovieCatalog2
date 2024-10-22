package com.example.kinoteka.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterDTO(
    @SerialName("userName")
    val login: String,
    @SerialName("name")
    val name: String,
    @SerialName("password")
    val password: String,
    @SerialName("email")
    val email: String,
    @SerialName("birthDate")
    val birthDate: String = "",
    @SerialName("gender")
    val gender: Int = 0
)
