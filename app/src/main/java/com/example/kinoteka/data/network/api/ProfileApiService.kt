package com.example.kinoteka.data.network.api

import com.example.kinoteka.constants.Constants.ACCOUNT_PROFILE_URL
import com.example.kinoteka.constants.Constants.ADD_REVIEW_URL
import com.example.kinoteka.constants.Constants.DELETE_REVIEW_URL
import com.example.kinoteka.constants.Constants.EDIT_REVIEW_URL
import com.example.kinoteka.data.entity.ProfileModel
import com.example.kinoteka.data.entity.ReviewModifyModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileApiService {
    @GET(ACCOUNT_PROFILE_URL)
    suspend fun getProfileInfo() : ProfileModel

    @PUT(ACCOUNT_PROFILE_URL)
    suspend fun updateAvatar(@Body profileModel: ProfileModel)

    @POST(ADD_REVIEW_URL)
    suspend fun addReview(@Path("movieId") movieId: String, @Body reviewModifyModel: ReviewModifyModel)

    @PUT(EDIT_REVIEW_URL)
    suspend fun editReview(@Path("movieId") movieId: String, @Path("id") id: String, @Body reviewModifyModel: ReviewModifyModel)

    @DELETE(DELETE_REVIEW_URL)
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("id") id: String)
}