package com.example.kinoteka.data.repository

import com.example.kinoteka.data.entity.MovieElementModel
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val networkMapper: NetworkMapper,
) : MovieRepository {
    override suspend fun getMovies(page: Int): List<Movie> {
        val response = movieApiService.getMovies(page)
        return networkMapper.fromEntityList(response.movies)
    }

    override suspend fun getMovieDetails() {
        TODO("Not yet implemented")
    }
}