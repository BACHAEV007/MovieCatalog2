package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.repository.AuthRepository

class LogoutUseCase (private val authRepository: AuthRepository){
    suspend operator fun invoke(){
        authRepository.logout()
    }
}