package com.example.kinoteka.data.repository

import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.ProfileApiService
import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.repository.ProfileRepository

class ProfileRepositoryImpl (
    private val profileApiService: ProfileApiService,
    private val networkMapper: NetworkMapper
) : ProfileRepository{
    override suspend fun getProfileInfo(): ProfileInfo {
        val response = profileApiService.getProfileInfo()
        return networkMapper.fromProfileModelToDomain(response)
    }
}