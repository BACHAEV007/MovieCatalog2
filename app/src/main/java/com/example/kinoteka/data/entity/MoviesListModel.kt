package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class MoviesListModel(
    val movies: List<MovieElementModel>
)
