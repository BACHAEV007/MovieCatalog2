package com.example.kinoteka.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesToUIContentMapper
import com.example.kinoteka.presentation.model.MovieContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieToUIContentMapper: MoviesToUIContentMapper
) : ViewModel() {
    private val _movieContent = MutableStateFlow(MovieContent())
    val movieContent: StateFlow<MovieContent> = _movieContent

    private val _allMovies = mutableListOf<MovieContent>()

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getMoviesUseCase(page)
            val uiContent = movieToUIContentMapper.mapList(movies)
            _allMovies.addAll(uiContent)
            getRandomMovie()
        }
    }

    fun getRandomMovie() {
        if (_allMovies.isNotEmpty()) {
            val randomMovie = _allMovies.random()
            _movieContent.value = randomMovie
        }
    }
}