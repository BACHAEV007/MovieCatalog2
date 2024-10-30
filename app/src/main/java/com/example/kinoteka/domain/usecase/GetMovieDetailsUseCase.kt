package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.domain.repository.MovieRepository
import com.example.kinoteka.domain.repository.ProfileRepository

class GetMovieDetailsUseCase (private val movieRepository: MovieRepository) {
    suspend operator fun invoke(id: String): MovieDetails {
        return movieRepository.getMovieDetails(id)
    }
}
