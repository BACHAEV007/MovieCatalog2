package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.ProfileInfo

interface ProfileRepository {
    suspend fun getProfileInfo(): ProfileInfo
    suspend fun updateAvatar(profileInfo: ProfileInfo)
}