package com.example.kinoteka.data.repository

import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.repository.FavouriteRepository

class FavouriteRepositoryImpl (
    private val favoritesApiService: FavouritesApiService,
    private val networkMapper: NetworkMapper,
    ) : FavouriteRepository {
    override suspend fun getFavorites(): List<Movie> {
        val response = favoritesApiService.getFavorites()
        return networkMapper.fromEntityList(response.movies)
    }
    override suspend fun addFavorite(id: String) {
        favoritesApiService.addFavorite(id)
    }

    override suspend fun deleteFavourite(id: String) {
        TODO("Not yet implemented")
    }

}

