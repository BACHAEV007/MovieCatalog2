package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.ReviewModify
import com.example.kinoteka.domain.repository.ProfileRepository

class EditReviewUseCase (private val profileRepository: ProfileRepository){
    suspend operator fun invoke(movieId: String, id: String, reviewModify: ReviewModify){
        profileRepository.editReview(movieId, id, reviewModify)
    }
}