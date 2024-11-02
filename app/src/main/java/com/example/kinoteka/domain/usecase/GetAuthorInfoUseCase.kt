package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.repository.MovieRepository

class GetAuthorInfoUseCase (private val movieRepository: MovieRepository) {
    suspend operator fun invoke(name: String): Author {
        return movieRepository.getAuthorInfo(name)
    }
}