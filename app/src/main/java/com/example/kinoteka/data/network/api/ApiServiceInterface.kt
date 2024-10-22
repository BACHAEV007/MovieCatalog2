package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.REGISTER_URL
import com.example.kinoteka.data.entity.UserRegisterDTO
import com.example.kinoteka.domain.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceInterface {
    @POST(REGISTER_URL)
    suspend fun register(@Body userRegisterBody: UserRegisterDTO): Token
}