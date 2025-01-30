package com.example.kinoteka.domain.usecase

import com.example.kinoteka.domain.model.User
import com.example.kinoteka.domain.repository.DatabaseRepository

class AddUserUseCase(private val databaseRepository: DatabaseRepository) {
    suspend operator fun invoke(user: User) {
        databaseRepository.addUser(user)
    }
}