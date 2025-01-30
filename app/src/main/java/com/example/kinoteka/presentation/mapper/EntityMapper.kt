package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.Friend
import com.example.kinoteka.domain.model.GenreDbModel
import com.example.kinoteka.domain.model.MovieDbModel
import com.example.kinoteka.domain.model.User
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import com.example.kinoteka.presentation.model.HiddenMovieContent
import com.example.kinoteka.presentation.model.UserContent

class EntityMapper {
    fun mapToContent(genreDbModel: GenreDbModel): GenreContent {
        return GenreContent(
            genreId = genreDbModel.genreId,
            userId = genreDbModel.userId,
            name = genreDbModel.name
        )
    }

    fun mapToDbModel(genreContent: GenreContent): GenreDbModel {
        return GenreDbModel(
            genreId = genreContent.genreId,
            userId = genreContent.userId,
            name = genreContent.name
        )
    }

    fun mapToContent(movieDbModel: MovieDbModel): HiddenMovieContent {
        return HiddenMovieContent(
            movieId = movieDbModel.movieId,
            userId = movieDbModel.userId,
            title = movieDbModel.title
        )
    }

    fun mapToDbModel(movieContent: HiddenMovieContent): MovieDbModel {
        return MovieDbModel(
            movieId = movieContent.movieId,
            userId = movieContent.userId,
            title = movieContent.title
        )
    }

    fun mapToContent(user: User): UserContent {
        return UserContent(
            userId = user.userId,
            nickName = user.nickName,
            avatar = user.avatar
        )
    }

    fun mapToDbModel(userContent: UserContent): User {
        return User(
            userId = userContent.userId,
            nickName = userContent.nickName,
            avatar = userContent.avatar
        )
    }

    fun mapToContent(friend: Friend): FriendContent {
        return FriendContent(
            friendId = friend.friendId,
            userId = friend.userId,
            nickName = friend.nickName,
            avatar = friend.avatar
        )
    }

    fun mapToDbModel(friendContent: FriendContent): Friend {
        return Friend(
            friendId = friendContent.friendId,
            userId = friendContent.userId,
            nickName = friendContent.nickName,
            avatar = friendContent.avatar
        )
    }
}