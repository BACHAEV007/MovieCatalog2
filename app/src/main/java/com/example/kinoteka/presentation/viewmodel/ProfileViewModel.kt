package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.presentation.mapper.ProfileMapper
import com.example.kinoteka.presentation.model.MovieContent
import com.example.kinoteka.presentation.model.ProfileContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class ProfileViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val profileMapper: ProfileMapper
) : ViewModel(){
    private val _profileContent = MutableStateFlow(ProfileContent())
    val profileContent: StateFlow<ProfileContent> = _profileContent

    fun fetchProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = getProfileInfoUseCase()
            val uiContent = profileMapper.mapToProfileUI(profile)
            _profileContent.value = uiContent
        }
    }

}