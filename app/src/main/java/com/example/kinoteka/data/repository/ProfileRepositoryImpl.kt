package com.example.kinoteka.data.repository

import com.example.kinoteka.data.entity.ProfileModel
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.ProfileApiService
import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.model.ReviewModify
import com.example.kinoteka.domain.repository.ProfileRepository

class ProfileRepositoryImpl (
    private val profileApiService: ProfileApiService,
    private val networkMapper: NetworkMapper
) : ProfileRepository{
    override suspend fun getProfileInfo(): ProfileInfo {
        val response = profileApiService.getProfileInfo()
        return networkMapper.fromProfileModelToDomain(response)
    }

    override suspend fun updateAvatar(profileInfo: ProfileInfo) {
        profileApiService.updateAvatar(networkMapper.fromProfileModelToData(profileInfo))
    }

    override suspend fun addReview(movieId: String, reviewModify: ReviewModify) {
        profileApiService.addReview(movieId, networkMapper.fromReviewModifyToData(reviewModify))
    }

    override suspend fun editReview(
        movieId: String,
        id: String,
        reviewModify: ReviewModify
    ) {
        profileApiService.editReview(movieId, id, networkMapper.fromReviewModifyToData(reviewModify))
    }

    override suspend fun deleteReview(movieId: String, id: String) {
        profileApiService.deleteReview(movieId, id)
    }
}