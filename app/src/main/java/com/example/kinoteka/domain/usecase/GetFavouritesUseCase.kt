package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.repository.FavouriteRepository

class GetFavouritesUseCase(private val favouriteRepository: FavouriteRepository) {
    suspend operator fun invoke(): List<Movie> {
        return favouriteRepository.getFavorites()
    }
}