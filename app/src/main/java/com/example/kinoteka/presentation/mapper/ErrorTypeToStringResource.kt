package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.R
import com.example.kinoteka.domain.model.ValidationErrorType

object ErrorTypeToStringResource {
    val errors = mapOf(
        ValidationErrorType.EMPTY_FIELD to R.string.empty_field_error,
        ValidationErrorType.INVALID_LOGIN_LENGTH to R.string.invalid_login_length,
        ValidationErrorType.INVALID_PASSWORD_LENGTH to R.string.invalid_password_length,
        ValidationErrorType.INVALID_NAME_LENGTH to R.string.invalid_name_length,
        ValidationErrorType.PASSWORDS_MISMATCH to R.string.mismatch_error,
        ValidationErrorType.INVALID_EMAIL to R.string.invalid_email_error,
        ValidationErrorType.INVALID_BIRTHDAY to R.string.invalid_birthday
    )
}