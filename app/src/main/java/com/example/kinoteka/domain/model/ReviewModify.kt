package com.example.kinoteka.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModify(
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)