package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PageInfoListDTO(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)
