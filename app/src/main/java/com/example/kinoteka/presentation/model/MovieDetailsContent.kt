package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.Genre
import com.example.kinoteka.domain.model.Review

data class MovieDetailsContent(
    val ageLimit: Int,
    val budget: String,
    val country: String,
    val description: String,
    val director: String,
    val fees: String,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<Review>,
    val tagline: String,
    val time: String,
    val year: Int,
    val averageRating: String
)
