package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.BASE_URL
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.network.interceptor.Interceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApiClient {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private fun createRetrofit(tokenDataSource: TokenDataSource): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                addInterceptor(Interceptor(tokenDataSource))
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun createAuthApi(tokenDataSource: TokenDataSource): AuthApiService {
        return createRetrofit(tokenDataSource).create(AuthApiService::class.java)
    }

    fun createMovieApi(tokenDataSource: TokenDataSource): MovieApiService {
        return createRetrofit(tokenDataSource).create(MovieApiService::class.java)
    }

    fun createFavouritesApi(tokenDataSource: TokenDataSource): FavouritesApiService {
        return createRetrofit(tokenDataSource).create(FavouritesApiService::class.java)
    }

    fun createProfileApi(tokenDataSource: TokenDataSource): ProfileApiService {
        return createRetrofit(tokenDataSource).create(ProfileApiService::class.java)
    }
}