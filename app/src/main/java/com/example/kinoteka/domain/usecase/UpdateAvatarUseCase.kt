package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.repository.ProfileRepository

class UpdateAvatarUseCase(private val profileRepository: ProfileRepository) {
    operator suspend fun invoke(profileInfo: ProfileInfo){
        profileRepository.updateAvatar(profileInfo)
    }
}