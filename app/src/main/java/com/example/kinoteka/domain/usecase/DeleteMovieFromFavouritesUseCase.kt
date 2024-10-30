package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.repository.FavouriteRepository

class DeleteMovieFromFavouritesUseCase (private val favouriteRepository: FavouriteRepository) {
    suspend operator fun invoke(id: String) {
        favouriteRepository.deleteFavourite(id)
    }
}