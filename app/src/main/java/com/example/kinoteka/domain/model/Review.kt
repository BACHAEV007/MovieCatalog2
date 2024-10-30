package com.example.kinoteka.domain.model

data class Review(
    val author: Author,
    val createDateTime: String,
    val id: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)

