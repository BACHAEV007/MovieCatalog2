package com.example.kinoteka.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.domain.model.LoginCredentialsModel
import com.example.kinoteka.domain.model.User
import com.example.kinoteka.domain.model.ValidationErrorType
import com.example.kinoteka.domain.usecase.AddUserUseCase
import com.example.kinoteka.domain.usecase.GetProfileInfoUseCase
import com.example.kinoteka.domain.usecase.LoginUserUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.mapper.ErrorTypeToStringResource
import com.example.kinoteka.presentation.model.LoginContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : ViewModel(){
    private val _loginContent = MutableStateFlow(LoginContent())
    val loginContent: StateFlow<LoginContent> = _loginContent
    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid
    init {
        viewModelScope.launch {
            _loginContent.collect {
                validateForm()
            }
        }
    }
    fun onLoginChanged(newLogin: String, context: Context) {
        val validationResult = validateLoginUseCase(newLogin)
        val errorDescription = getErrorDescription(validationResult)
        viewModelScope.launch(Dispatchers.Main) {
            if (validationResult != null) {
                Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
            }
        }
        _loginContent.update { currentState ->
            currentState.copy(
                login = newLogin,
                loginError = errorDescription
            )
        }
    }

    fun onPasswordChanged(password: String, context: Context){
        val validationResult = validatePasswordUseCase(password)
        val errorDescription = getErrorDescription(validationResult)
        _loginContent.value = _loginContent.value.copy(
            password = password,
            passwordError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm() {
        _isFormValid.value = _loginContent.value.login.isNotEmpty() &&
                _loginContent.value.password.isNotEmpty() &&
                _loginContent.value.loginError == null &&
                _loginContent.value.passwordError == null
    }

    fun onLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            loginUserUseCase(
                with(_loginContent.value) {
                    LoginCredentialsModel(
                        userName = login,
                        password = password
                    )
                }
            )
            val user = getProfileInfoUseCase()
            addUserUseCase(
                User(
                    userId = user.id,
                    nickName = user.nickName,
                    avatar = user.avatarLink ?: "",
                )
            )
        }
    }

    private fun getErrorDescription(validationErrorType: ValidationErrorType?): Int? {
        if (validationErrorType == null) {
            return null
        }
        return ErrorTypeToStringResource.errors[validationErrorType]
    }



}