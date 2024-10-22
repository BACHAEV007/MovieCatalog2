package com.example.kinoteka.data.mapper

import com.example.kinoteka.data.entity.UserRegisterDTO
import com.example.kinoteka.domain.model.UserRegisterModel

class NetworkMapper {
    fun UserRegisterModel.toDTO(): UserRegisterDTO {
        return UserRegisterDTO(
            login = this.login,
            name = this.name,
            email = this.email,
            password = this.password,
            birthDate = this.birthDate,
            gender = this.gender
        )
    }
}