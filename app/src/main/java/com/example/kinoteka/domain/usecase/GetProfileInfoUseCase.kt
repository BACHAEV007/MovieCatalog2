package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.repository.ProfileRepository

class GetProfileInfoUseCase(private val profileRepository: ProfileRepository) {
    operator suspend fun invoke() : ProfileInfo{
        return profileRepository.getProfileInfo()
    }
}