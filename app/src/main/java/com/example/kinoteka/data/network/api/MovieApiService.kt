package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.GET_MOVIES_URL
import com.example.kinoteka.constants.Constants.GET_MOVIE_DETAILS_URL
import com.example.kinoteka.data.entity.FilmSearchByFiltersResponse
import com.example.kinoteka.data.entity.MovieDetailsModel
import com.example.kinoteka.data.entity.MoviesPagedListDTO
import com.example.kinoteka.data.entity.PersonByNameResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieApiService {
    @GET(GET_MOVIES_URL)
    suspend fun getMovies(@Path("page") page: Int): MoviesPagedListDTO

    @GET(GET_MOVIE_DETAILS_URL)
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetailsModel

    @GET
    suspend fun getMovieRating(
        @Url url: String = "https://kinopoiskapiunofficial.tech/api/v2.2/films",
        @Query("keyword") title: String,
        @Query("yearFrom") yearFrom: Int = 1000,
        @Header("X-API-KEY") apiKey: String
    ): FilmSearchByFiltersResponse

    @GET
    suspend fun getAuthorInfo(
        @Url url: String = "https://kinopoiskapiunofficial.tech/api/v1/persons",
        @Query("name") name: String,
        @Header("X-API-KEY") apiKey: String
    ): PersonByNameResponse
}