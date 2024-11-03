package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.MovieContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieToUIContentMapper: MoviesMapper
) : ViewModel() {
    private val _movieContent = MutableStateFlow<List<MovieContent>>(emptyList())
    val movieContent: StateFlow<List<MovieContent>> = _movieContent
    private val _allMovies = mutableListOf<MovieContent>()
    private var currentPage = 0

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getMoviesUseCase(page)
            val uiContent = movieToUIContentMapper.mapMoviesToContentList(movies)
            _allMovies.clear()
            _allMovies.addAll(uiContent)
            getRandomMovies()
        }
    }

    fun getRandomMovies() {
        val remainingMovies = _allMovies
        val randomMovies = remainingMovies.shuffled().take(6)
        _movieContent.value = randomMovies
    }

    fun loadNewMovies() {
        currentPage++
        if (currentPage == 6){
            currentPage = 1
        }
        fetchMovies(currentPage)
    }

    fun removeMovie() {
        _movieContent.value = _movieContent.value.drop(1)
    }

}