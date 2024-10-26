package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int): List<Movie>
    suspend fun getMovieDetails()
}