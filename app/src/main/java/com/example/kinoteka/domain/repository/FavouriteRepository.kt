package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.Movie

interface FavouriteRepository {
    suspend fun getFavorites(): List<Movie>
    suspend fun addFavorite(id: String)
    suspend fun deleteFavourite(id: String)
}