package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.AuthApiService
import com.example.kinoteka.data.network.api.ProfileApiService
import com.example.kinoteka.data.repository.AuthRepositoryImpl
import com.example.kinoteka.data.repository.FavouriteRepositoryImpl
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.data.repository.ProfileRepositoryImpl
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.domain.usecase.LogoutUseCase
import com.example.kinoteka.domain.usecase.UpdateAvatarUseCase
import com.example.kinoteka.presentation.mapper.ProfileMapper
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel
import com.example.kinoteka.presentation.viewmodel.ProfileViewModel

class ProfileViewModelFactory (
    private val profileApiService: ProfileApiService,
    private val authApiService: AuthApiService,
    private val tokenDataSource: TokenDataSource,
    private val networkMapper: NetworkMapper,
    private val profileMapper: ProfileMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            val profileRepository = ProfileRepositoryImpl(profileApiService, networkMapper)
            val getProfileInfoUseCase = GetProfileInfoUseCase(profileRepository)
            val authRepository = AuthRepositoryImpl(authApiService, tokenDataSource, networkMapper)
            val logoutUseCase = LogoutUseCase(authRepository)
            val updateAvatarUseCase = UpdateAvatarUseCase(profileRepository)
            return ProfileViewModel(
                updateAvatarUseCase,
                logoutUseCase,
                getProfileInfoUseCase,
                profileMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}