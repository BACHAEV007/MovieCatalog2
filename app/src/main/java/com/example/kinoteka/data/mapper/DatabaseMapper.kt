package com.example.kinoteka.data.mapper

import com.example.kinoteka.data.dbentity.FriendEntity
import com.example.kinoteka.data.dbentity.GenreEntity
import com.example.kinoteka.data.dbentity.HiddenMoviesEntity
import com.example.kinoteka.data.dbentity.UserEntity
import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.model.User

class DatabaseMapper {
    fun mapToDomain(friendEntity: FriendEntity): Friend {
        return Friend(
            friendId = friendEntity.friendId,
            userId = friendEntity.userId,
            nickName = friendEntity.nickName,
            avatar = friendEntity.avatar
        )
    }

    fun mapToEntity(friend: Friend): FriendEntity {
        return FriendEntity(
            friendId = friend.friendId,
            userId = friend.userId,
            nickName = friend.nickName,
            avatar = friend.avatar
        )
    }

    fun mapToDomain(genreEntity: GenreEntity): GenreDbModel {
        return GenreDbModel(
            genreId = genreEntity.genreId,
            userId = genreEntity.userId,
            name = genreEntity.name
        )
    }

    fun mapToEntity(genre: GenreDbModel): GenreEntity {
        return GenreEntity(
            genreId = genre.genreId,
            userId = genre.userId,
            name = genre.name
        )
    }

    fun mapToDomain(movieEntity: HiddenMoviesEntity): MovieDbModel {
        return MovieDbModel(
            movieId = movieEntity.movieId,
            userId = movieEntity.userId,
            title = movieEntity.title
        )
    }

    fun mapToEntity(movie: MovieDbModel): HiddenMoviesEntity {
        return HiddenMoviesEntity(
            movieId = movie.movieId,
            userId = movie.userId,
            title = movie.title
        )
    }

    fun mapToDomain(userEntity: UserEntity): User {
        return User(
            userId = userEntity.userId,
            nickName = userEntity.nickName,
            avatar = userEntity.avatar
        )
    }

    fun mapToEntity(user: User): UserEntity {
        return UserEntity(
            userId = user.userId,
            nickName = user.nickName,
            avatar = user.avatar
        )
    }
}