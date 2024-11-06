package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.User
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class FetchUserUseCase(private val databaseRepository: DatabaseRepository) {
    operator fun invoke(): Flow<User> {
        return databaseRepository.fetchUser()
    }
}