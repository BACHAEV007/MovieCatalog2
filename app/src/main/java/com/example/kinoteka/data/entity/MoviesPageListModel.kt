package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class MoviesPagedListDTO(
    val pageInfo: PageInfoListDTO,
    val movies: List<MovieElementModel>
)
