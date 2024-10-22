package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.ValidationErrorType


data class RegistrationContent(
    val login: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthday: String = "",
    val loginError: Int? = null,
    val passwordError:  Int? = null,
    val confirmPasswordError:  Int? = null,
    val emailError: Int? = null,
    val nameErrorType:  Int? = null,
    val birthdayErrorType:  Int? = null
)
