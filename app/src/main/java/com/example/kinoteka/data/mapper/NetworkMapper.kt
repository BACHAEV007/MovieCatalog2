package com.example.kinoteka.data.mapper

import com.example.kinoteka.data.entity.LoginCredentials
import com.example.kinoteka.data.entity.MovieElementModel
import com.example.kinoteka.data.entity.MoviesListModel
import com.example.kinoteka.data.entity.ProfileModel
import com.example.kinoteka.data.entity.UserRegisterDTO
import com.example.kinoteka.domain.model.Gender
import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.model.UserRegisterModel

class NetworkMapper {
    fun UserRegisterModel.toDTO(): UserRegisterDTO {
        return UserRegisterDTO(
            userName = this.userName,
            name = this.name,
            email = this.email,
            password = this.password,
            birthDate = this.birthDate,
            gender = this.gender
        )
    }
    fun LoginCredentialsModel.toDTO(): LoginCredentials {
        return LoginCredentials(
            userName = this.userName,
            password = this.password
        )
    }

    fun fromEntity(movieElementModel: MovieElementModel): Movie {
        return Movie(
            id = movieElementModel.id,
            name = movieElementModel.name,
            poster = movieElementModel.poster,
            year = movieElementModel.year,
            country = movieElementModel.country,
            genres = movieElementModel.genres,
            averageRating = movieElementModel.reviews.sumOf { it.rating } / movieElementModel.reviews.size.toFloat()
        )
    }

    fun fromMovieListDataToDomain(moviesListModel: MoviesListModel): List<Movie> {
        return fromEntityList(moviesListModel.movies)
    }

    fun fromEntityList(networkMovies: List<MovieElementModel>): List<Movie> {
        return networkMovies.map { fromEntity(it) }
    }

    fun fromProfileModelToDomain(profileModel: ProfileModel): ProfileInfo{
        return ProfileInfo(
            nickName = profileModel.nickName,
            name = profileModel.name,
            avatarLink = profileModel.avatarLink ?: "",
            email = profileModel.email,
            birthDate = profileModel.birthDate,
            gender = if (profileModel.gender == 0) Gender.MALE else Gender.FEMALE
        )
    }
}