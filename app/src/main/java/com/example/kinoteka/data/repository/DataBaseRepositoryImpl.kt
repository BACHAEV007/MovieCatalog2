package com.example.kinoteka.data.repository

import com.example.kinoteka.data.dao.FriendDao
import com.example.kinoteka.data.dao.GenreDao
import com.example.kinoteka.data.dao.MovieDao
import com.example.kinoteka.data.dao.UserDao
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.model.User
import com.example.kinoteka.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataBaseRepositoryImpl(
    private val userDao: UserDao,
    private val movieDao: MovieDao,
    private val friendDao: FriendDao,
    private val genreDao: GenreDao,
    private val mapper: DatabaseMapper
) : DatabaseRepository {
    override suspend fun addFriend(friend: Friend) {
        friendDao.addFriend(mapper.mapToEntity(friend))
    }

    override suspend fun deleteFriend(friend: Friend) {
        friendDao.deleteFriend(mapper.mapToEntity(friend))
    }

    override fun fetchFriends(): Flow<List<Friend>> {
        return friendDao.fetchFriend()
            .map { friendEntities ->
                friendEntities.map { friendEntity ->
                    mapper.mapToDomain(friendEntity)
                }
            }
    }

    override suspend fun addGenre(genre: GenreDbModel) {
        genreDao.addGenre(mapper.mapToEntity(genre))
    }

    override suspend fun deleteGenre(genre: GenreDbModel) {
        genreDao.deleteGenre(mapper.mapToEntity(genre))
    }

    override fun fetchGenres(): Flow<List<GenreDbModel>> {
        return genreDao.fetchGenre()
            .map { genreEntities ->
                genreEntities.map { genreEntity ->
                    mapper.mapToDomain(genreEntity)
                }
            }
    }

    override suspend fun addMovie(movie: MovieDbModel) {
        movieDao.addMovie(mapper.mapToEntity(movie))
    }

    override suspend fun deleteMovie(movie: MovieDbModel) {
        movieDao.deleteMovie(mapper.mapToEntity(movie))
    }

    override fun fetchMovies(): Flow<List<MovieDbModel>> {
        return movieDao.fetchMovie().map { movieEntities ->
            movieEntities.map { movieEntity ->
                mapper.mapToDomain(movieEntity)
            }
        }
    }

    override suspend fun addUser(user: User) {
        userDao.addUser(mapper.mapToEntity(user))
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(mapper.mapToEntity(user))
    }

    override fun fetchUser(): Flow<User> {
        return userDao.fetchUser()
            .map {
                user -> mapper.mapToDomain(user)
            }
    }
}