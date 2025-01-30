package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.AuthRepositoryImpl
import com.example.kinoteka.data.repository.DataBaseRepositoryImpl
import com.example.kinoteka.data.repository.ProfileRepositoryImpl
import com.example.kinoteka.domain.usecase.AddUserUseCase
import com.example.kinoteka.domain.usecase.DeleteFriendUseCase
import com.example.kinoteka.domain.usecase.FetchFriendsUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.domain.usecase.LoginUserUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.viewmodel.FriendsViewModel
import com.example.kinoteka.presentation.viewmodel.LoginViewModel

class FriendsViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)) {
            val databaseMapper = DatabaseMapper()
            val entityMapper = EntityMapper()
            val databaseRepository = DataBaseRepositoryImpl(databaseMapper)
            val fetchFriendsUseCase = FetchFriendsUseCase(databaseRepository)
            val deleteFriendUseCase = DeleteFriendUseCase(databaseRepository)
            return FriendsViewModel(
                entityMapper,
                fetchFriendsUseCase,
                deleteFriendUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
