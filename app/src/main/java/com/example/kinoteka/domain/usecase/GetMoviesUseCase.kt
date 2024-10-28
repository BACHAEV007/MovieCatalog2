package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.repository.MovieRepository

class GetMoviesUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int): List<Movie> {
        return movieRepository.getMovies(page)
    }
}