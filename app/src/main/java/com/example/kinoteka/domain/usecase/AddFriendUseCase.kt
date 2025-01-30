package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.repository.DatabaseRepository

class AddFriendUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(friend: Friend) {
        databaseRepository.addFriend(friend)
    }
}