package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.ValidationErrorType

class ValidateConfirmPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String) : ValidationErrorType?{
        return when {
            repeatedPassword.isEmpty() -> ValidationErrorType.EMPTY_FIELD
            password != repeatedPassword -> ValidationErrorType.PASSWORDS_MISMATCH
            else -> null
        }
    }
}