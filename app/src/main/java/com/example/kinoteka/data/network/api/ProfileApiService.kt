package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.ACCOUNT_PROFILE_URL
import com.example.kinoteka.data.entity.ProfileModel
import retrofit2.http.GET

interface ProfileApiService {
    @GET(ACCOUNT_PROFILE_URL)
    suspend fun getProfileInfo() : ProfileModel
}