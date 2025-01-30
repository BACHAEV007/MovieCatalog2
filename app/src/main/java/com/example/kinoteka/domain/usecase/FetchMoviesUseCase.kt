package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<MovieDbModel>> {
        return databaseRepository.fetchMovies()
    }
}