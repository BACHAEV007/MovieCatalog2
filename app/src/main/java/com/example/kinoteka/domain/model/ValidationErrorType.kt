package com.example.kinoteka.domain.model

enum class ValidationErrorType {
    INVALID_LOGIN_LENGTH,
    INVALID_EMAIL,
    INVALID_PASSWORD_LENGTH,
    PASSWORDS_MISMATCH,
    INVALID_NAME_LENGTH,
    INVALID_BIRTHDAY,

    EMPTY_FIELD
}