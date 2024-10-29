package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.presentation.model.ProfileContent
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileMapper {
    fun mapToProfileUI(profileInfo: ProfileInfo): ProfileContent {
        return ProfileContent(
            nickName = profileInfo.nickName,
            name = profileInfo.name,
            avatarLink = profileInfo.avatarLink,
            email = profileInfo.email,
            birthDate = formatDate(profileInfo.birthDate),
            gender = profileInfo.gender
        )
    }

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
            outputFormat.format(date!!)
        } catch (e: Exception) {

            dateString
        }
    }
}