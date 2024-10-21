package com.example.kinoteka.domain.usecase

import android.util.Patterns
import com.example.kinoteka.domain.model.ValidationErrorType

class ValidateEmailUseCase {

    operator fun invoke(email: String): ValidationErrorType? {
        return when {
            email.isEmpty() -> ValidationErrorType.EMPTY_FIELD
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationErrorType.INVALID_EMAIL
            else -> null
        }
    }

}