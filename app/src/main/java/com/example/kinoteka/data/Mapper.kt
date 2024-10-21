package com.example.kinoteka.data

import com.example.kinoteka.domain.model.LoginCredentials
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel

class Mapper : (LoginCredentials) -> RegistrationViewModel {
    override fun invoke(p1: LoginCredentials): RegistrationViewModel {
        TODO("Not yet implemented")
    }
}