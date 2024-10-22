package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApiClient {

    val apiClient: ApiServiceInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(ApiServiceInterface::class.java)
    }

}