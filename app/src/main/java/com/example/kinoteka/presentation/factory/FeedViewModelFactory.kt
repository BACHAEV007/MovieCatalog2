package com.example.kinoteka.presentation.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.data.repository.MovieRepositoryImpl
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.FeedViewModel

class FeedViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            val tokenDataSource = TokenDataSource(context)
            val networkMapper = NetworkMapper()
            val apiService = RetrofitApiClient.createMovieApi(tokenDataSource)
            val movieRepository = MovieRepositoryImpl(apiService, networkMapper)
            val getMoviesUseCase = GetMoviesUseCase(movieRepository)
            val movieToUIContentMapper = MoviesMapper()
            return FeedViewModel(getMoviesUseCase, movieToUIContentMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}