package com.example.kinoteka.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.AuthRepositoryImpl
import com.example.kinoteka.domain.usecase.LoginUserUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val validateLoginUseCase = ValidateLoginUseCase()
            val validatePasswordUseCase = ValidatePasswordUseCase()

            val tokenDataSource = TokenDataSource(context)
            val networkMapper = NetworkMapper()
            val apiService = RetrofitApiClient.apiClient
            val authRepository = AuthRepositoryImpl(apiService, tokenDataSource, networkMapper)
            val loginUserUseCase = LoginUserUseCase(authRepository)

            return LoginViewModel(
                validateLoginUseCase,
                validatePasswordUseCase,
                loginUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}