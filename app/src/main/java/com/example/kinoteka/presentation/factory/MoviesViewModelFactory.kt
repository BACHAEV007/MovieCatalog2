package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.repository.FavouriteRepositoryImpl
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel

class MoviesViewModelFactory(
    private val favoritesApiService: FavouritesApiService,
    private val movieApiService: MovieApiService,
    private val networkMapper: NetworkMapper,
    private val movieToUIContentMapper: MoviesMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            val movieRepository = MovieRepositoryImpl(movieApiService, networkMapper)
            val getMoviesUseCase = GetMoviesUseCase(movieRepository)
            val favouriteRepository = FavouriteRepositoryImpl(favoritesApiService, networkMapper)
            val getFavouritesUseCase = GetFavouritesUseCase(favouriteRepository)
            val addMovieToFavoritesUseCase = AddMovieToFavoritesUseCase(favouriteRepository)
            return MoviesViewModel(addMovieToFavoritesUseCase, getFavouritesUseCase, getMoviesUseCase, movieToUIContentMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}