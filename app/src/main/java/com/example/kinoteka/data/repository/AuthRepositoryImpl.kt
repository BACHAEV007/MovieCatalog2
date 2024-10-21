package com.example.kinoteka.data.repository

import com.example.kinoteka.domain.model.LoginCredentials
import com.example.kinoteka.domain.model.UserRegisterModel
import com.example.kinoteka.domain.repository.AuthRepository

class AuthRepositoryImpl() : AuthRepository {
    override suspend fun register(userRegisterModel: UserRegisterModel){
        /*реализация апи*/
    }
    override suspend fun login(loginBody: LoginCredentials){
        //loginBody.toDomain()
    }
}