package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.ADD_FAVORITE_URL
import com.example.kinoteka.constants.Constants.DELETE_FAVORITE_URL
import com.example.kinoteka.constants.Constants.GET_FAVORITES_URL
import com.example.kinoteka.data.entity.MoviesListModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavouritesApiService {
    @GET(GET_FAVORITES_URL)
    suspend fun getFavorites(): MoviesListModel
    @POST(ADD_FAVORITE_URL)
    suspend fun addFavorite(@Path("id") id: String)
    @DELETE(DELETE_FAVORITE_URL)
    suspend fun deleteFavorite(@Path("id") id: String)
}