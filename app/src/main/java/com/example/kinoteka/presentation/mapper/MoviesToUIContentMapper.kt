package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.Genre
import com.example.kinoteka.domain.model.Movie
import com.example.kinoteka.presentation.model.MovieContent

class MoviesToUIContentMapper {
    fun map(movie: Movie): MovieContent {
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
//        reviews = movie.reviews.map { review ->
//            ReviewUIModel(
//                id = review.id,
//                rating = review.rating
//            )
//        }
    )
}
    fun mapList(movies: List<Movie>): List<MovieContent> {
        return movies.map { map(it) }
    }
}