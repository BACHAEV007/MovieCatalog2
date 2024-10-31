package com.example.kinoteka.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.DeleteMovieFromFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMovieDetailsUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.MovieDetailsContent
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val deleteMovieFromFavouritesUseCase: DeleteMovieFromFavouritesUseCase,
    private val getMoviesDetailsUseCase: GetMovieDetailsUseCase,
    private val contentMapper: MoviesMapper
) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsContent?> = mutableStateOf(null)
    val movieDetails: State<MovieDetailsContent?>// = _movieDetails
        get() = _movieDetails

    private val _isFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isFavorite: State<Boolean> = _isFavorite

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val movieDetailsModel = getMoviesDetailsUseCase(movieId)
                _movieDetails.value = contentMapper.mapToMovieDetailsContent(movieDetailsModel)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToFavorites(movieId: String) {
        viewModelScope.launch {
            try {
                addMovieToFavoritesUseCase(movieId)
                _isFavorite.value = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeFromFavorites(movieId: String) {
        viewModelScope.launch {
            try {
                deleteMovieFromFavouritesUseCase(movieId)
                _isFavorite.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}