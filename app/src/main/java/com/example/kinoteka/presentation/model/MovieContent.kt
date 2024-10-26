package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.Genre

data class MovieContent(
    val id: String = "",
    val name: String = "",
    val poster: String = "",
    val year: String = "Год",
    val country: String = "Страна",
    val genres: List<Genre> = emptyList()
    //val reviews: List<ReviewShort>?,
    //val rating: Double?,
    //var reviewRating: Int? = null
)
