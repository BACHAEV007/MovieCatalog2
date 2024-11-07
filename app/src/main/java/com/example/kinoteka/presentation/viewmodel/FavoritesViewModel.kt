package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.DeleteGenreUseCase
import com.example.kinoteka.domain.usecase.FetchGenresUseCase
import com.example.kinoteka.domain.usecase.GetFavouritesUseCase
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.GenreContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoritesViewModel (
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val fetchGenresUseCase: FetchGenresUseCase,
    private val deleteGenreUseCase: DeleteGenreUseCase,
    private val movieToUIContentMapper: MoviesMapper,
    private val entityMapper: EntityMapper
) : ViewModel() {
    private val _favouritesContent = MutableStateFlow<List<FavouriteContent>>(emptyList())
    val favouritesContent: StateFlow<List<FavouriteContent>> = _favouritesContent
    init{
        fetchFavourites()
    }
    val genresContent: Flow<List<GenreContent>> = fetchGenresUseCase()
        .map { genreDbList ->
            genreDbList.map { entityMapper.mapToContent(it) }
        }

    fun fetchFavourites(){
        viewModelScope.launch(Dispatchers.IO) {
            val movies = getFavouritesUseCase()
            val uiContent = movieToUIContentMapper.mapFavouritesToContentList(movies)
            _favouritesContent.value = (uiContent)
        }
    }

    fun deleteGenre(genre: GenreContent) {
        viewModelScope.launch {
            deleteGenreUseCase(entityMapper.mapToDbModel(genre))
        }
    }
}