package com.example.kinoteka.domain.repository

import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun addFriend(friend: Friend)
    suspend fun deleteFriend(friend: Friend)
    fun fetchFriends(): Flow<List<Friend>>

    suspend fun addGenre(genre: GenreDbModel)
    suspend fun deleteGenre(genre: GenreDbModel)
    fun fetchGenres(): Flow<List<GenreDbModel>>

    suspend fun addMovie(movie: MovieDbModel)
    suspend fun deleteMovie(movie: MovieDbModel)
    fun fetchMovies(): Flow<List<MovieDbModel>>

    suspend fun addUser(user: User)
    suspend fun deleteUser(user: User)
    fun fetchUser(): Flow<User>
}