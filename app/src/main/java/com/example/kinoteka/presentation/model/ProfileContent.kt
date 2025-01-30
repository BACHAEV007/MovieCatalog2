package com.example.kinoteka.presentation.model

import com.example.kinoteka.domain.model.Gender

data class ProfileContent(
    val nickName: String = "",
    val name: String = "",
    val avatarLink: String? = null,
    val email: String = "",
    val birthDate: String = "",
    val gender: Gender = Gender.MALE
)
