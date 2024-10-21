package com.example.kinoteka.domain.model

data class UserRegisterModel(
    val login: String,
    val email: String,
    val name: String,
    val password: String,
    val birthDate: String,
    val gender: Int
)
