package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.REGISTER_URL
import com.example.kinoteka.domain.Token
import com.example.kinoteka.domain.model.UserRegisterModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST(REGISTER_URL)
    suspend fun register(@Body userRegisterModel: UserRegisterModel): Token
}