package com.example.kinoteka.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.DeleteMovieFromFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetAuthorInfoUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMovieDetailsUseCase
import com.example.kinoteka.domain.usecase.GetMovieRatingUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.MovieDetailsContent
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val deleteMovieFromFavouritesUseCase: DeleteMovieFromFavouritesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getMovieRatingUseCase: GetMovieRatingUseCase,
    private val getMoviesDetailsUseCase: GetMovieDetailsUseCase,
    private val getAuthorInfoUseCase: GetAuthorInfoUseCase,
    private val contentMapper: MoviesMapper
) : ViewModel() {
    private val _movieDetails: MutableState<MovieDetailsContent?> = mutableStateOf(null)
    val movieDetails: State<MovieDetailsContent?>
        get() = _movieDetails

    private val _movieRating: MutableState<MovieRating?> = mutableStateOf(null)
    val movieRating: State<MovieRating?>
        get() = _movieRating

    private val _isFavorite: MutableState<Boolean> = mutableStateOf(false)
    val isFavorite: State<Boolean>
        get() = _isFavorite

    private val _authorContent: MutableState<Author?> = mutableStateOf(null)
    val authorContent: State<Author?>
        get() = _authorContent

    fun loadMovieDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val favouriteMovies = getFavouritesUseCase()
                val movieDetailsModel = getMoviesDetailsUseCase(movieId)
                val director = movieDetailsModel.director.substringBefore(",")
                val author = getAuthorInfoUseCase(director)
                val movieRating = getMovieRatingUseCase(movieDetailsModel.name, movieDetailsModel.year)
                _isFavorite.value = favouriteMovies.any { it.id == movieDetailsModel.id }
                _movieDetails.value = contentMapper.mapToMovieDetailsContent(movieDetailsModel)
                _movieRating.value = movieRating
                _authorContent.value = author
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