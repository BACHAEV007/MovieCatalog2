package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
    val author: AuthorModel?,
    val createDateTime: String,
    val id: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)
