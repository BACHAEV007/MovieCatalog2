package com.example.kinoteka.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class AuthorModel(
    val avatar: String?,
    val nickName: String,
    val userId: String
)
