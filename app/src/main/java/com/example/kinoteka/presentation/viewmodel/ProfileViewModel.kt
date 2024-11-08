package com.example.kinoteka.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.domain.usecase.LogoutUseCase
import com.example.kinoteka.domain.usecase.UpdateAvatarUseCase
import com.example.kinoteka.presentation.mapper.ProfileMapper
import com.example.kinoteka.presentation.model.ProfileContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProfileViewModel(
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val profileMapper: ProfileMapper
) : ViewModel(){
    private val _profileContent = MutableStateFlow(ProfileContent())
    val profileContent: StateFlow<ProfileContent> = _profileContent
    private val _navigateToSignInScreen = MutableLiveData<Boolean>()
    val navigateToSignInScreen: LiveData<Boolean> get() = _navigateToSignInScreen
    fun onLogout() {
        _navigateToSignInScreen.value = true
    }

    fun onNavigatedToSignInScreen() {
        _navigateToSignInScreen.value = false
    }
    fun fetchProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val profile = getProfileInfoUseCase()
                val uiContent = profileMapper.mapToProfileUI(profile)
                _profileContent.value = uiContent
            }
            catch (e: Exception) {
                if (e is HttpException && e.code() == 401) {
                    withContext(Dispatchers.Main) {
                        onLogout()
                    }
                }
            }
        }

    }

    fun updateAvatar(avatarLink: String?){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (avatarLink.isNullOrEmpty() || !Patterns.WEB_URL.matcher(avatarLink).matches()) {
                    throw IllegalArgumentException("Некорректная ссылка на картинку")
                }
                val updatedContent = _profileContent.value.copy(avatarLink = avatarLink)
                _profileContent.value = updatedContent

                val domainProfile = profileMapper.mapToProfileDomain(updatedContent)
                updateAvatarUseCase(domainProfile)
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Некорректная ссылка на картинку")
            } catch (e: Exception) {
                throw IllegalArgumentException("Некорректная ссылка на картинку")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}