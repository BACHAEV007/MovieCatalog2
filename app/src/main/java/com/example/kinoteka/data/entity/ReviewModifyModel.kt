package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModifyModel(
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)