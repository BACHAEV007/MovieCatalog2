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
    val gson = GsonBuilder()
        .setLenient()
        .create()
    fun createAuthApi(tokenDataSource: TokenDataSource): ApiServiceInterface {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                addInterceptor(Interceptor(tokenDataSource))
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }

            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiServiceInterface::class.java)
    }
    fun createMovieApi(tokenDataSource: TokenDataSource): MovieApiService {
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                addInterceptor(Interceptor(tokenDataSource))
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(MovieApiService::class.java)
    }
}