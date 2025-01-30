package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class FilmSearchByFiltersResponse(
    val items: List<FilmSearchItem>,
    val total: Int,
    val totalPages: Int
)

