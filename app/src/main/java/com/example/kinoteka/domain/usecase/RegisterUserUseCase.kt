package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.UserRegisterModel
import com.example.kinoteka.domain.repository.AuthRepository

class RegisterUserUseCase (private val authRepository: AuthRepository) {
    suspend operator fun invoke(userRegisterModel: UserRegisterModel){
        authRepository.register(userRegisterModel)
    }
}