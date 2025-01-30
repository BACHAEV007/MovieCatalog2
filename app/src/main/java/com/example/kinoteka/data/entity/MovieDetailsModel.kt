package com.example.kinoteka.data.entity

import com.example.kinoteka.domain.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsModel(
    val ageLimit: Int,
    val budget: Int,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewModel>,
    val tagline: String,
    val time: Int,
    val year: Int
)

