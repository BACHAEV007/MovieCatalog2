package com.example.kinoteka.presentation.mapper

import com.example.kinoteka.domain.model.ProfileInfo
import com.example.kinoteka.presentation.model.ProfileContent
import com.example.kinoteka.utils.DateFormatter
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileMapper {
    fun mapToProfileUI(profileInfo: ProfileInfo): ProfileContent {
        return ProfileContent(
            nickName = profileInfo.nickName,
            name = profileInfo.name,
            avatarLink = profileInfo.avatarLink,
            email = profileInfo.email,
            birthDate = DateFormatter.formatDate(profileInfo.birthDate),
            gender = profileInfo.gender
        )
    }

    fun mapToProfileDomain(profileContent: ProfileContent): ProfileInfo {
        return ProfileInfo(
            nickName = profileContent.nickName,
            name = profileContent.name,
            avatarLink = profileContent.avatarLink,
            email = profileContent.email,
            birthDate = DateFormatter.parseDateToServerFormat(profileContent.birthDate),
            gender = profileContent.gender
        )
    }
}