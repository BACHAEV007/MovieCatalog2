package com.example.kinoteka.data.repository

import com.example.kinoteka.AppContext
import com.example.kinoteka.data.dao.FriendDao
import com.example.kinoteka.data.dao.GenreDao
import com.example.kinoteka.data.dao.MovieDao
import com.example.kinoteka.data.dao.UserDao
import com.example.kinoteka.data.database.MovieDataBase
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.model.User
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataBaseRepositoryImpl(
    private val mapper: DatabaseMapper
) : DatabaseRepository {
    val db = MovieDataBase.getDatabase(AppContext.context)
    override suspend fun addFriend(friend: Friend) {
        db.FriendDao().addFriend(mapper.mapToEntity(friend))
    }

    override suspend fun deleteFriend(friend: Friend) {
        db.FriendDao().deleteFriend(mapper.mapToEntity(friend))
    }

    override fun fetchFriends(): Flow<List<Friend>> {
        return db.FriendDao().fetchFriend()
            .map { friendEntities ->
                friendEntities.map { friendEntity ->
                    mapper.mapToDomain(friendEntity)
                }
            }
    }

    override suspend fun addGenre(genre: GenreDbModel) {
        db.GenreDao().addGenre(mapper.mapToEntity(genre))
    }

    override suspend fun deleteGenre(genre: GenreDbModel) {
        db.GenreDao().deleteGenre(mapper.mapToEntity(genre))
    }

    override fun fetchGenres(): Flow<List<GenreDbModel>> {
        return db.GenreDao().fetchGenre()
            .map { genreEntities ->
                genreEntities.map { genreEntity ->
                    mapper.mapToDomain(genreEntity)
                }
            }
    }

    override suspend fun addMovie(movie: MovieDbModel) {
        db.MovieDao().addMovie(mapper.mapToEntity(movie))
    }

    override suspend fun deleteMovie(movie: MovieDbModel) {
        db.MovieDao().deleteMovie(mapper.mapToEntity(movie))
    }

    override fun fetchMovies(): Flow<List<MovieDbModel>> {
        return db.MovieDao().fetchMovie().map { movieEntities ->
            movieEntities.map { movieEntity ->
                mapper.mapToDomain(movieEntity)
            }
        }
    }

    override suspend fun addUser(user: User) {
        db.UserDao().addUser(mapper.mapToEntity(user))
    }

    override suspend fun deleteUser(user: User) {
        db.UserDao().deleteUser(mapper.mapToEntity(user))
    }

    override fun fetchUser(): Flow<User> {
        return db.UserDao().fetchUser()
            .map {
                user -> mapper.mapToDomain(user)
            }
    }
}