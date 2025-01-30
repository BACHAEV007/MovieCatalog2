package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.repository.AuthRepository

class LoginUserUseCase (private val authRepository: AuthRepository){
    suspend operator fun invoke(loginCredentials: LoginCredentialsModel){
        authRepository.login(loginCredentials)
    }
}