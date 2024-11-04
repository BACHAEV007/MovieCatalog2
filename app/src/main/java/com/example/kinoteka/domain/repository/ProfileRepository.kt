package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.model.ReviewModify

interface ProfileRepository {
    suspend fun getProfileInfo(): ProfileInfo
    suspend fun updateAvatar(profileInfo: ProfileInfo)
    suspend fun addReview(movieId: String, reviewModify: ReviewModify)
    suspend fun editReview(movieId: String, id: String, reviewModify: ReviewModify)
    suspend fun deleteReview(movieId: String, id: String)
}