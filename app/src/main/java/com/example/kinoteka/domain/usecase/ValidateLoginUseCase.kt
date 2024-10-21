package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.constants.Constants.MIN_USERNAME_LENGTH
import com.example.kinoteka.domain.model.ValidationErrorType

class ValidateLoginUseCase {
    operator fun invoke(login: String) : ValidationErrorType?{
        return when {
            login.isEmpty() -> ValidationErrorType.EMPTY_FIELD
            login.length < MIN_USERNAME_LENGTH -> ValidationErrorType.INVALID_LOGIN_LENGTH
            else -> null
        }
    }
}