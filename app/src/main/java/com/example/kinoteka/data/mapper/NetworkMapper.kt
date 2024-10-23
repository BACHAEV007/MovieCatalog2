package com.example.kinoteka.data.mapper

import com.example.kinoteka.data.entity.LoginCredentialsDTO
import com.example.kinoteka.data.entity.UserRegisterDTO
import com.example.kinoteka.domain.model.LoginCredentials
import com.example.kinoteka.domain.model.UserRegisterModel

class NetworkMapper {
    fun UserRegisterModel.toDTO(): UserRegisterDTO {
        return UserRegisterDTO(
            userName = this.userName,
            name = this.name,
            email = this.email,
            password = this.password,
            birthDate = this.birthDate,
            gender = this.gender
        )
    }
    fun LoginCredentials.toDTO(): LoginCredentialsDTO{
        return LoginCredentialsDTO(
            userName = this.userName,
            password = this.password
        )
    }
}