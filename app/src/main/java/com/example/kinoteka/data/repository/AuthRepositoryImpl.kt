package com.example.kinoteka.data.repository

import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.ApiServiceInterface
import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.UserRegisterModel
import com.example.kinoteka.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiServiceInterface,
    private val tokenDataSource: TokenDataSource,
    private val networkMapper: NetworkMapper,
) : AuthRepository {
    override suspend fun register(userRegisterModel: UserRegisterModel){
        val userRegisterDTO = networkMapper.run { userRegisterModel.toDTO() }
        tokenDataSource.saveToken(
            apiService.register(userRegisterDTO).token
        )
    }
    override suspend fun login(loginCredentials: LoginCredentialsModel){
        val LoginCredentialsDTO = networkMapper.run { loginCredentials.toDTO() }
        tokenDataSource.saveToken(
            apiService.login(LoginCredentialsDTO).token
        )
    }
}