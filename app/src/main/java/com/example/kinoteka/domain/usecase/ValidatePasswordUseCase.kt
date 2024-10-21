package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.constants.Constants.MIN_PASSWORD_LENGTH
import com.example.kinoteka.domain.model.ValidationErrorType

class ValidatePasswordUseCase {
    operator fun invoke(password: String) : ValidationErrorType?{
        return when {
            password.isEmpty() -> ValidationErrorType.EMPTY_FIELD
            password.length < MIN_PASSWORD_LENGTH -> ValidationErrorType.INVALID_PASSWORD_LENGTH
            else -> null
        }
    }
}