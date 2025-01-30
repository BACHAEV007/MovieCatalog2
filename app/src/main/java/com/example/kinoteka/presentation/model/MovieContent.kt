package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.Genre

data class MovieContent(
    val id: String = "",
    val name: String = "Название",
    val poster: String = "",
    val year: String = "Год",
    val country: String = "Страна",
    val genres: List<Genre> = emptyList(),
    var progress: Int = 0,
    val rating: Float? = 0.0f
)
