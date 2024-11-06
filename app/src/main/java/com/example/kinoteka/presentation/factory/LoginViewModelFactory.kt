package com.example.kinoteka.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.AuthRepositoryImpl
import com.example.kinoteka.data.repository.DataBaseRepositoryImpl
import com.example.kinoteka.data.repository.ProfileRepositoryImpl
import com.example.kinoteka.domain.usecase.AddFriendUseCase
import com.example.kinoteka.domain.usecase.AddUserUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.domain.usecase.LoginUserUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.viewmodel.LoginViewModel

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val validateLoginUseCase = ValidateLoginUseCase()
            val validatePasswordUseCase = ValidatePasswordUseCase()

            val tokenDataSource = TokenDataSource(context)
            val networkMapper = NetworkMapper()
            val apiService = RetrofitApiClient.createAuthApi(tokenDataSource)
            val authRepository = AuthRepositoryImpl(apiService, tokenDataSource, networkMapper)
            val loginUserUseCase = LoginUserUseCase(authRepository)
            val databaseMapper = DatabaseMapper()
            val profileApiService = RetrofitApiClient.createProfileApi(tokenDataSource)
            val profileRepository = ProfileRepositoryImpl(profileApiService, networkMapper)
            val getProfileInfoUseCase = GetProfileInfoUseCase(profileRepository)
            val databaseRepository = DataBaseRepositoryImpl(databaseMapper)
            val addUserUseCase = AddUserUseCase(databaseRepository)
            return LoginViewModel(
                validateLoginUseCase,
                validatePasswordUseCase,
                loginUserUseCase,
                addUserUseCase,
                getProfileInfoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}