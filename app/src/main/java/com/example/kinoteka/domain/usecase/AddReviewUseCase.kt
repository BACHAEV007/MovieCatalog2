package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.ReviewModify
import com.example.kinoteka.domain.repository.AuthRepository
import com.example.kinoteka.domain.repository.ProfileRepository

class AddReviewUseCase (private val profileRepository: ProfileRepository){
    suspend operator fun invoke(movieId: String, reviewModify: ReviewModify){
        profileRepository.addReview(movieId, reviewModify)
    }
}