package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.repository.DatabaseRepository

class DeleteMovieUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(movie: MovieDbModel) {
        databaseRepository.deleteMovie(movie)
    }
}