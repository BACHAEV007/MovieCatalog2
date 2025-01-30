package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.ReviewModify
import com.example.kinoteka.domain.repository.ProfileRepository

class DeleteReviewUseCase (private val profileRepository: ProfileRepository){
    suspend operator fun invoke(movieId: String, id: String){
        profileRepository.deleteReview(movieId, id)
    }
}