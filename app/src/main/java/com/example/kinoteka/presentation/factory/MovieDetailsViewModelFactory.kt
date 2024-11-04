package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.network.api.ProfileApiService
import com.example.kinoteka.data.repository.FavouriteRepositoryImpl
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.data.repository.ProfileRepositoryImpl
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.AddReviewUseCase
import com.example.kinoteka.domain.usecase.DeleteMovieFromFavouritesUseCase
import com.example.kinoteka.domain.usecase.DeleteReviewUseCase
import com.example.kinoteka.domain.usecase.EditReviewUseCase
import com.example.kinoteka.domain.usecase.GetAuthorInfoUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMovieDetailsUseCase
import com.example.kinoteka.domain.usecase.GetMovieRatingUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel

class MovieDetailsViewModelFactory (
    private val favoritesApiService: FavouritesApiService,
    private val movieApiService: MovieApiService,
    private val profileApiService: ProfileApiService,
    private val networkMapper: NetworkMapper,
    private val contentMapper: MoviesMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            val movieRepository = MovieRepositoryImpl(movieApiService, networkMapper)
            val favouriteRepository = FavouriteRepositoryImpl(favoritesApiService, networkMapper)
            val profileRepository = ProfileRepositoryImpl(profileApiService, networkMapper)
            val getMoviesDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
            val addMovieToFavoritesUseCase = AddMovieToFavoritesUseCase(favouriteRepository)
            val getMovieRatingUseCase = GetMovieRatingUseCase(movieRepository)
            val getAuthorInfoUseCase = GetAuthorInfoUseCase(movieRepository)
            val getFavouritesUseCase = GetFavouritesUseCase(favouriteRepository)
            val getProfileInfoUseCase = GetProfileInfoUseCase(profileRepository)
            val deleteMovieFromFavouritesUseCase = DeleteMovieFromFavouritesUseCase(favouriteRepository)
            val addReviewUseCase = AddReviewUseCase(profileRepository)
            val editReviewUseCase = EditReviewUseCase(profileRepository)
            val deleteReviewUseCase = DeleteReviewUseCase(profileRepository)
            return MovieDetailsViewModel(
                addMovieToFavoritesUseCase,
                deleteMovieFromFavouritesUseCase,
                getFavouritesUseCase,
                getMovieRatingUseCase,
                getMoviesDetailsUseCase,
                getAuthorInfoUseCase,
                addReviewUseCase,
                editReviewUseCase,
                deleteReviewUseCase,
                getProfileInfoUseCase,
                contentMapper
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}