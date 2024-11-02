package com.example.kinoteka.data.mapper

import com.example.kinoteka.data.entity.AuthorModel
import com.example.kinoteka.data.entity.FilmSearchByFiltersResponse
import com.example.kinoteka.data.entity.LoginCredentials
import com.example.kinoteka.data.entity.MovieDetailsModel
import com.example.kinoteka.data.entity.MovieElementModel
import com.example.kinoteka.data.entity.MoviesListModel
import com.example.kinoteka.data.entity.PersonByNameResponse
import com.example.kinoteka.data.entity.ProfileModel
import com.example.kinoteka.data.entity.ReviewModel
import com.example.kinoteka.data.entity.UserRegisterDTO
import com.example.kinoteka.domain.model.Author
import com.example.kinoteka.domain.model.Gender
import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.domain.model.MovieRating
import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.domain.model.Review
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

    fun fromMovieDetailsDataToDomain(movieDetailsModel: MovieDetailsModel): MovieDetails {
        return MovieDetails(
            ageLimit = movieDetailsModel.ageLimit,
            budget = movieDetailsModel.budget,
            country = movieDetailsModel.country,
            description = movieDetailsModel.description,
            director = movieDetailsModel.director,
            fees = movieDetailsModel.fees,
            genres = movieDetailsModel.genres,
            id = movieDetailsModel.id,
            name = movieDetailsModel.name,
            poster = movieDetailsModel.poster,
            reviews = movieDetailsModel.reviews.map { reviewModel ->
                Review(
                    author = reviewModel.author?.let { fromAuthorModelToAuthor(it) },
                    createDateTime = reviewModel.createDateTime,
                    id = reviewModel.id,
                    isAnonymous = reviewModel.isAnonymous,
                    rating = reviewModel.rating,
                    reviewText = reviewModel.reviewText
                )
            },
            tagline = movieDetailsModel.tagline,
            time = movieDetailsModel.time,
            year = movieDetailsModel.year
        )
    }

    fun fromAuthorModelToAuthor(authorModel: AuthorModel): Author {
        return Author(
            avatar = authorModel?.avatar ?: "",
            nickName = authorModel.nickName,
            userId = authorModel.userId
        )
    }


    fun fromEntityList(networkMovies: List<MovieElementModel>): List<Movie> {
        return networkMovies.map { fromEntity(it) }
    }

    fun fromProfileModelToDomain(profileModel: ProfileModel): ProfileInfo{
        return ProfileInfo(
            nickName = profileModel.nickName,
            name = profileModel.name,
            avatarLink = profileModel.avatarLink,
            email = profileModel.email,
            birthDate = profileModel.birthDate,
            gender = if (profileModel.gender == 0) Gender.MALE else Gender.FEMALE
        )
    }

    fun fromMovieRatingDataToDomain(film: FilmSearchByFiltersResponse): MovieRating {
        return MovieRating(
            ratingKinopoisk = film.items[0].ratingKinopoisk.toString(),
            ratingImdb = film.items[0].ratingImdb.toString(),
            id = film.items[0].kinopoiskId
        )
    }

    fun fromProfileModelToData(profileInfo: ProfileInfo): ProfileModel{
        return ProfileModel(
            nickName = profileInfo.nickName,
            name = profileInfo.name,
            avatarLink = profileInfo.avatarLink,
            email = profileInfo.email,
            birthDate = profileInfo.birthDate,
            gender = profileInfo.gender.ordinal
        )
    }

    fun fromAuthorInfoToDomain(personByNameResponse: PersonByNameResponse) : Author{
        return Author(
            nickName = personByNameResponse.items[0].nameRu,
            avatar = personByNameResponse.items[0].posterUrl,
            userId = personByNameResponse.items[0].webUrl
        )
    }
}