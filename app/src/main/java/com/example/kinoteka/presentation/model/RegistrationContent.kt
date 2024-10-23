package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.Gender
import com.example.kinoteka.domain.model.ValidationErrorType


data class RegistrationContent(
    val login: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthday: String = "",
    val dateOfBirthMillis: Long? = null,
    val loginError: Int? = null,
    val passwordError:  Int? = null,
    val confirmPasswordError:  Int? = null,
    val emailError: Int? = null,
    val nameErrorType:  Int? = null,
    val birthdayErrorType:  Int? = null,
    val gender: Gender = Gender.MALE,
    val uniqueLoginError: Int? = null
)
