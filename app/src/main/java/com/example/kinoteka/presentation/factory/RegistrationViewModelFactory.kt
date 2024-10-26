package com.example.kinoteka.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.AuthRepositoryImpl
import com.example.kinoteka.domain.usecase.RegisterUserUseCase
import com.example.kinoteka.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.kinoteka.domain.usecase.ValidateEmailUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidateNameUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel

class RegistrationViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            val validateLoginUseCase = ValidateLoginUseCase()
            val validateEmailUseCase = ValidateEmailUseCase()
            val validateNameUseCase = ValidateNameUseCase()
            val validatePasswordUseCase = ValidatePasswordUseCase()
            val validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()

            val tokenDataSource = TokenDataSource(context)
            val networkMapper = NetworkMapper()
            val apiService = RetrofitApiClient.createAuthApi(tokenDataSource)
            val authRepository = AuthRepositoryImpl(apiService, tokenDataSource, networkMapper)
            val registerUserUseCase = RegisterUserUseCase(authRepository)

            return RegistrationViewModel(
                validateLoginUseCase,
                validateEmailUseCase,
                validatePasswordUseCase,
                validateNameUseCase,
                validateConfirmPasswordUseCase,
                registerUserUseCase,
                context
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}