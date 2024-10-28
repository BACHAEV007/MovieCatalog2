package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.UserRegisterModel

interface AuthRepository {
    suspend fun register(userRegisterModel: UserRegisterModel)
    suspend fun login(loginBody: LoginCredentialsModel)
}