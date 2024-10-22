package com.example.kinoteka.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.kinoteka.domain.usecase.ValidateEmailUseCase
import com.example.kinoteka.domain.model.ValidationErrorType
import com.example.kinoteka.domain.usecase.RegisterUserUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.kinoteka.domain.usecase.ValidateNameUseCase
import com.example.kinoteka.presentation.mapper.ErrorTypeToStringResource
import com.example.kinoteka.presentation.model.RegistrationContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegistrationViewModel(
    private val validateLoginUseCase: ValidateLoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateRepeatedPasswordUseCase: ValidateConfirmPasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(){
    private val _registrationContent = MutableStateFlow(RegistrationContent())
    val registrationContent: StateFlow<RegistrationContent> = _registrationContent

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid: StateFlow<Boolean> = _isFormValid

    private fun validateForm(context: Context ?= null) {
        _isFormValid.value = _registrationContent.value.name.isNotEmpty() &&
                _registrationContent.value.login.isNotEmpty() &&
                _registrationContent.value.email.isNotEmpty() &&
                _registrationContent.value.password.isNotEmpty() &&
                _registrationContent.value.confirmPassword.isNotEmpty() &&
                _registrationContent.value.nameErrorType == null &&
                _registrationContent.value.loginError == null &&
                _registrationContent.value.emailError == null &&
                _registrationContent.value.passwordError == null &&
                _registrationContent.value.confirmPasswordError == null &&
                _registrationContent.value.birthday.isNotEmpty()
    }

    fun onLoginChanged(newLogin: String, context: Context) {
        val validationResult = validateLoginUseCase(newLogin)
        val errorDescription = getErrorDescription(validationResult)
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Логин валиден", Toast.LENGTH_SHORT).show()
        }
        _registrationContent.update { currentState ->
            currentState.copy(
                login = newLogin,
                loginError = errorDescription
            )
        }
        validateForm(context)
    }

    fun onEmailChanged(email: String, context: Context) {
        val validationResult = validateEmailUseCase(email)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            email = email,
            emailError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "email валиден ${email}", Toast.LENGTH_SHORT).show()
        }
        validateForm(context)
    }

    fun onNameChanged(name: String, context: Context) {
        val validationResult = validateNameUseCase(name)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            name = name,
            nameErrorType = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "name валиден ${name}", Toast.LENGTH_SHORT).show()
        }
        validateForm(context)
    }

    fun onPasswordChanged(password: String, context: Context){
        val validationResult = validatePasswordUseCase(password)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            password = password,
            passwordError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "пароль валиден", Toast.LENGTH_SHORT).show()
        }
        validateForm(context)
    }

    fun onRepeatPasswordChanged(confirmPassword: String, context: Context){
        val password = _registrationContent.value.password
        val validationResult = validateRepeatedPasswordUseCase(password, confirmPassword)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "пароли совпадают", Toast.LENGTH_SHORT).show()
        }
        validateForm(context)
    }

    fun onDateChange(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        val formattedDate = dateFormat.format(calendar.time)
        _registrationContent.update { currentState ->
            currentState.copy(birthday = formattedDate)
        }
        validateForm()
    }

    private fun getErrorDescription(validationErrorType: ValidationErrorType?): Int? {
        if (validationErrorType == null) {
            return null
        }
        return ErrorTypeToStringResource.errors[validationErrorType]
    }
}