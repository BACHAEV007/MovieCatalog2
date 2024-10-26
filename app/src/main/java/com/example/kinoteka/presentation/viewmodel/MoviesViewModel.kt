package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesToUIContentMapper
import com.example.kinoteka.presentation.model.MovieContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieToUIContentMapper: MoviesToUIContentMapper
) : ViewModel() {
    private val _carouselContent = MutableStateFlow<List<MovieContent>>(emptyList())
    val carouselContent: StateFlow<List<MovieContent>> = _carouselContent

    private val _allMovies = mutableListOf<MovieContent>()

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getMoviesUseCase(page)
            val uiContent = movieToUIContentMapper.mapList(movies)
            _allMovies.addAll(uiContent)
            getCarouselMovies()
        }
    }

    private fun getCarouselMovies() {
        val firstFiveMovies = _allMovies.take(5)
        _carouselContent.value = firstFiveMovies
    }
}