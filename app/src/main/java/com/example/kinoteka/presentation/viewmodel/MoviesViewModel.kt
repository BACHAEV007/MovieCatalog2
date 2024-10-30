package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.MovieContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieToUIContentMapper: MoviesMapper
) : ViewModel() {
    private val _carouselContent = MutableStateFlow<List<MovieContent>>(emptyList())
    val carouselContent: StateFlow<List<MovieContent>> = _carouselContent

    private val _favouritesContent = MutableStateFlow<List<FavouriteContent>>(emptyList())
    val favouritesContent: StateFlow<List<FavouriteContent>> = _favouritesContent

    private val _gridMoviesContent = MutableStateFlow<List<MovieContent>>(emptyList())
    val gridMoviesContent: StateFlow<List<MovieContent>> = _gridMoviesContent

    private val _allMovies = mutableListOf<MovieContent>()

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getMoviesUseCase(page)
            val uiContent = movieToUIContentMapper.mapMoviesToContentList(movies)
            _allMovies.addAll(uiContent)
            withContext(Dispatchers.Main) {
                val carouselMovies = _allMovies.take(5)
                _carouselContent.value = carouselMovies

                _gridMoviesContent.value = _allMovies.filterNot { movie ->
                    carouselMovies.any { carouselMovie -> carouselMovie.id == movie.id }
                }
            }
        }
    }

    fun fetchFavourites(){
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getFavouritesUseCase()
            val uiContent = movieToUIContentMapper.mapFavouritesToContentList(movies)
            _favouritesContent.value = (uiContent)
        }
    }

    fun addMovieToFavourite(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val movies = addMovieToFavoritesUseCase(id)
        }
    }

    fun getRandomMovie(): String {
        val movies = _allMovies
        return movies.random().id
    }

    fun getCarouselMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val firstFiveMovies = _allMovies.take(5)
            _carouselContent.value = firstFiveMovies
        }
    }
}