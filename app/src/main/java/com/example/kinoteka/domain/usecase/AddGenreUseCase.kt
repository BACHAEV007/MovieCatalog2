package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.repository.DatabaseRepository

class AddGenreUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(genre: GenreDbModel) {
        databaseRepository.addGenre(genre)
    }
}