package com.example.kinoteka.data.entity

import com.example.kinoteka.domain.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class MovieElementModel(
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<Genre>,
    val reviews: List<ReviewShortModel>
)