package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.domain.model.MovieRating

interface MovieRepository {
    suspend fun getMovies(page: Int): List<Movie>
    suspend fun getMovieDetails(id: String) : MovieDetails
    suspend fun getMovieRating(title: String, year: Int) : MovieRating
    suspend fun getAuthorInfo(name: String): Author
}