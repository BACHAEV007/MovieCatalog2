package com.example.kinoteka.domain.model

data class Movie(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<Genre>,
    //val reviews: List<ReviewShort>,
    //val rating: Double?,
    //var reviewRating: Int? = null
)
