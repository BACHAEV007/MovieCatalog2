package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PersonItem(
    val kinopoiskId: Int,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val sex: String,
    val webUrl: String
)