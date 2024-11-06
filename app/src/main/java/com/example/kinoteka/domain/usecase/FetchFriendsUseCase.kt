package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class FetchFriendsUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<List<Friend>> {
        return databaseRepository.fetchFriends()
    }
}