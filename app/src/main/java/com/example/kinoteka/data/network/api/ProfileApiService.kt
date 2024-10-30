package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.ACCOUNT_PROFILE_URL
import com.example.kinoteka.data.entity.ProfileModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApiService {
    @GET(ACCOUNT_PROFILE_URL)
    suspend fun getProfileInfo() : ProfileModel

    @PUT(ACCOUNT_PROFILE_URL)
    suspend fun updateAvatar(@Body profileModel: ProfileModel)
}