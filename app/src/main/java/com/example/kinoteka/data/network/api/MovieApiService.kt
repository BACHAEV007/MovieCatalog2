package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.GET_MOVIES_URL
import com.example.kinoteka.constants.Constants.GET_MOVIE_DETAILS_URL
import com.example.kinoteka.data.entity.MovieDetailsModel
import com.example.kinoteka.data.entity.MoviesPagedListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET(GET_MOVIES_URL)
    suspend fun getMovies(@Path("page") page: Int): MoviesPagedListDTO

    @GET(GET_MOVIE_DETAILS_URL)
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetailsModel
}