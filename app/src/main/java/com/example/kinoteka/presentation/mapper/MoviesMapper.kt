package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.Genre
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.presentation.model.FavouriteContent
import com.example.kinoteka.presentation.model.MovieContent
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
            rating = ((movie.averageRating * 10).roundToInt() / 10.0f).toFloat()
        )
    }

    fun mapToFavouriteContent(movie: Movie): FavouriteContent {
        return FavouriteContent(
            id = movie.id,
            poster = movie.poster,
            rating = ((movie.averageRating * 10).roundToInt() / 10.0f).toString()
        )
    }

    fun mapMoviesToContentList(movies: List<Movie>): List<MovieContent> {
        return movies.map { mapToMovieContent(it) }
    }

    fun mapFavouritesToContentList(movies: List<Movie>): List<FavouriteContent> {
        return movies.map { mapToFavouriteContent(it) }
    }
}