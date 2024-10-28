package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.repository.FavouriteRepository

class AddMovieToFavoritesUseCase (private val favouriteRepository: FavouriteRepository) {
    suspend operator fun invoke(id: String) {
        favouriteRepository.addFavorite(id)
    }
}