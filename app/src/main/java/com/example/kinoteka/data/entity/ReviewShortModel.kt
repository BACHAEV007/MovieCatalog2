package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewShortModel(
    val id: String,
    val rating: Int
)
