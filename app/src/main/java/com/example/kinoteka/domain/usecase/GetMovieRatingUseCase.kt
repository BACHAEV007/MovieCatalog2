package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.repository.MovieRepository

class GetMovieRatingUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(title: String, year: Int): MovieRating {
        return movieRepository.getMovieRating(title, year)
    }
}