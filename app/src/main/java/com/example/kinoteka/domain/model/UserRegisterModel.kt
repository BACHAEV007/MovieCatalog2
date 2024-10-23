package com.example.kinoteka.domain.model

import java.time.LocalDateTime

data class UserRegisterModel(
    val userName: String,
    val email: String,
    val name: String,
    val password: String,
    val birthDate: String,
    val gender: Int = 0
)
