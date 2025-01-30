package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class FetchGenresUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<GenreDbModel>> {
        return databaseRepository.fetchGenres()
    }
}