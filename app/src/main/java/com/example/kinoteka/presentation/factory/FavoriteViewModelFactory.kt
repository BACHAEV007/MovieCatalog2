package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.network.api.ProfileApiService
import com.example.kinoteka.data.repository.DataBaseRepositoryImpl
import com.example.kinoteka.data.repository.FavouriteRepositoryImpl
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.data.repository.ProfileRepositoryImpl
import com.example.kinoteka.domain.usecase.AddFriendUseCase
import com.example.kinoteka.domain.usecase.AddGenreUseCase
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.AddReviewUseCase
import com.example.kinoteka.domain.usecase.DeleteGenreUseCase
import com.example.kinoteka.domain.usecase.DeleteMovieFromFavouritesUseCase
import com.example.kinoteka.domain.usecase.DeleteReviewUseCase
import com.example.kinoteka.domain.usecase.EditReviewUseCase
import com.example.kinoteka.domain.usecase.FetchFriendsUseCase
import com.example.kinoteka.domain.usecase.FetchGenresUseCase
import com.example.kinoteka.domain.usecase.GetAuthorInfoUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMovieDetailsUseCase
import com.example.kinoteka.domain.usecase.GetMovieRatingUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel

class FavoriteViewModelFactory (
    private val favoritesApiService: FavouritesApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            var networkMapper = NetworkMapper()
            var contentMapper = MoviesMapper()
            val entityMapper = EntityMapper()
            val favouriteRepository = FavouriteRepositoryImpl(favoritesApiService, networkMapper)
            val getFavouritesUseCase = GetFavouritesUseCase(favouriteRepository)
            val databaseMapper = DatabaseMapper()
            val databaseRepository = DataBaseRepositoryImpl(databaseMapper)
            val deleteGenreUseCase = DeleteGenreUseCase(databaseRepository)
            val fetchGenresUseCase = FetchGenresUseCase(databaseRepository)
            return FavoritesViewModel(
                getFavouritesUseCase,
                fetchGenresUseCase,
                deleteGenreUseCase,
                contentMapper,
                entityMapper,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}