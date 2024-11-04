package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.Genre
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.domain.model.MovieDetails
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.MovieContent
import com.example.kinoteka.presentation.model.MovieDetailsContent
import com.example.kinoteka.utils.DateFormatter
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class MoviesMapper {
    fun mapToMovieContent(movie: Movie): MovieContent {
        return MovieContent(
            id = movie.id,
            name = movie.name,
            poster = movie.poster,
            year = movie.year.toString(),
            country = movie.country,
            genres = movie.genres.map { genre ->
                Genre(
                    id = genre.id,
                    name = genre.name
                )
            },
            rating = ((movie.averageRating * 10).roundToInt() / 10.0f)
        )
    }

    fun mapToFavouriteContent(movie: Movie): FavouriteContent {
        return FavouriteContent(
            id = movie.id,
            poster = movie.poster,
            rating = ((movie.averageRating * 10).roundToInt() / 10.0f).toString()
        )
    }

    fun mapToMovieDetailsContent(movieDetails: MovieDetails): MovieDetailsContent {
        val formattedBudget = formatNumberWithSpaces(movieDetails.budget)
        val formattedFees = formatNumberWithSpaces(movieDetails.fees)
        val formattedTime = DateFormatter.formatTime(movieDetails.time)
        val formattedReviews = movieDetails.reviews.map { review ->
            review.copy(createDateTime = DateFormatter.formatDate(review.createDateTime))
        }
        return MovieDetailsContent(
            ageLimit = movieDetails.ageLimit,
            budget = formattedBudget,
            country = movieDetails.country,
            description = movieDetails.description,
            director = movieDetails.director,
            fees = formattedFees,
            genres = movieDetails.genres,
            id = movieDetails.id,
            name = movieDetails.name,
            poster = movieDetails.poster,
            reviews = formattedReviews,
            tagline = movieDetails.tagline,
            time = formattedTime,
            year = movieDetails.year
        )
    }

    fun formatNumberWithSpaces(number: Int): String {
        return NumberFormat.getInstance(Locale.US).format(number)
    }

    fun mapMoviesToContentList(movies: List<Movie>): List<MovieContent> {
        return movies.map { mapToMovieContent(it) }
    }

    fun mapFavouritesToContentList(movies: List<Movie>): List<FavouriteContent> {
        return movies.map { mapToFavouriteContent(it) }
    }
}