package com.example.kinoteka.presentation.model

data class LoginContent(
    val login: String = "",
    val loginError: Int? = null,
    val password: String = "",
    val passwordError:  Int? = null,
    val invalidError: Int? = null
)
