package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.constants.Constants.MIN_NAME_LENGTH
import com.example.kinoteka.domain.model.ValidationErrorType

class ValidateNameUseCase {
    operator fun invoke(name: String) : ValidationErrorType?{
        return when {
            name.isEmpty() -> ValidationErrorType.EMPTY_FIELD
            name.length < MIN_NAME_LENGTH -> ValidationErrorType.INVALID_NAME_LENGTH
            else -> null
        }
    }
}