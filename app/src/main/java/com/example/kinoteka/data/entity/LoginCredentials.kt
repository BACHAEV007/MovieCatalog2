package com.example.kinoteka.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentials(
    @SerialName("userName")
    val userName: String,
    @SerialName("password")
    val password: String
)
