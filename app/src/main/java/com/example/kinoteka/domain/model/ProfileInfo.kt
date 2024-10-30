package com.example.kinoteka.domain.model

import kotlinx.serialization.SerialName

data class ProfileInfo(
    val nickName: String = "",
    val name: String = "",
    val avatarLink: String? = "",
    val email: String = "",
    val birthDate: String = "",
    val gender: Gender = Gender.MALE
)
