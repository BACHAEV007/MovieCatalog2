package com.example.kinoteka.data.repository

import android.util.Log
import com.example.kinoteka.data.entity.MovieElementModel
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val networkMapper: NetworkMapper,
) : MovieRepository {
    override suspend fun getMovies(page: Int): List<Movie> {
        val response = movieApiService.getMovies(page)
        return networkMapper.fromEntityList(response.movies)
    }

    override suspend fun getMovieDetails(id: String): MovieDetails {
        val response = movieApiService.getMovieDetails(id)
        return networkMapper.fromMovieDetailsDataToDomain(response)
    }

    override suspend fun getMovieRating(title: String, year: Int): MovieRating {
        val response = movieApiService.getMovieRating(
            url = "https://kinopoiskapiunofficial.tech/api/v2.2/films",
            title = title,
            yearFrom = year,
            "e5b63938-c0b9-41e9-bb53-68ed13f2e1ac"
        )
        return networkMapper.fromMovieRatingDataToDomain(response)
    }

    override suspend fun getAuthorInfo(name: String): Author {
        val response = movieApiService.getAuthorInfo(
            url = "https://kinopoiskapiunofficial.tech/api/v1/persons",
            name = name,
            "e5b63938-c0b9-41e9-bb53-68ed13f2e1ac"
        )
        return networkMapper.fromAuthorInfoToDomain(response)
    }
}