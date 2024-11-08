package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.AddMovieToFavoritesUseCase
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.MovieContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FeedViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val movieToUIContentMapper: MoviesMapper,
    private val addMovieToFavoritesUseCase: AddMovieToFavoritesUseCase
) : ViewModel() {
    private val _movieContent = MutableStateFlow<List<MovieContent>>(emptyList())
    val movieContent: StateFlow<List<MovieContent>> = _movieContent
    private val _allMovies = mutableListOf<MovieContent>()
    private var currentPage = 0
    private val _navigateToSignInScreen = MutableLiveData<Boolean>()
    val navigateToSignInScreen: LiveData<Boolean> get() = _navigateToSignInScreen

    fun onLogout() {
        _navigateToSignInScreen.value = true
    }

    fun onNavigatedToSignInScreen() {
        _navigateToSignInScreen.value = false
    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movies = getMoviesUseCase(page)
                val uiContent = movieToUIContentMapper.mapMoviesToContentList(movies)
                _allMovies.clear()
                _allMovies.addAll(uiContent)
                getRandomMovies()
            } catch (e: Exception) {
                if (e is HttpException && e.code() == 401) {
                    withContext(Dispatchers.Main) {
                        onLogout()
                    }
                }
            }
        }
    }

    fun addToFavorites(movieId: String) {
        viewModelScope.launch {
            try {
                addMovieToFavoritesUseCase(movieId)
            } catch (e: HttpException) {
                if (e.code() == 400) {
                }
            } catch (e: Exception) {
            }
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