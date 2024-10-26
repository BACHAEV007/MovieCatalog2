package com.example.kinoteka.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesToUIContentMapper
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel

class MoviesViewModelFactory(
    private val tokenDataSource: TokenDataSource,
    private val apiService: MovieApiService,
    private val networkMapper: NetworkMapper,
    private val movieToUIContentMapper: MoviesToUIContentMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            val movieRepository = MovieRepositoryImpl(apiService, networkMapper)
            val getMoviesUseCase = GetMoviesUseCase(movieRepository)
            return MoviesViewModel(getMoviesUseCase, movieToUIContentMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}