package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class PersonByNameResponse(
    val items: List<PersonItem>,
    val total: Int
)

