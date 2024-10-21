package com.example.kinoteka.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.kinoteka.domain.usecase.ValidateEmailUseCase
import com.example.kinoteka.domain.model.ValidationErrorType
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.domain.usecase.ValidateConfirmPasswordUseCase
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
    private val validateRepeatedPasswordUseCase: ValidateConfirmPasswordUseCase
//    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(){
    private val _registrationContent = MutableStateFlow(RegistrationContent())
    val registrationContent: StateFlow<RegistrationContent> = _registrationContent

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun onLoginChanged(newLogin: String, context: Context) {
        val validationResult = validateLoginUseCase(newLogin)
        val errorDescription = getErrorDescription(validationResult)
        // Проверка на наличие ошибки и показ Toast
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
            Toast.makeText(context, "email валиден", Toast.LENGTH_SHORT).show()
        }
    }

    fun onPasswordChanged(password: String, context: Context){
        val validationResult = validatePasswordUseCase(password)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            password = password,
            emailError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "пароль валиден", Toast.LENGTH_SHORT).show()
        }
    }

    fun onRepeatPasswordChanged(confirmPassword: String, context: Context){
        val password = _registrationContent.value.password
        val validationResult = validateRepeatedPasswordUseCase(password, confirmPassword)
        val errorDescription = getErrorDescription(validationResult)
        _registrationContent.value = _registrationContent.value.copy(
            confirmPassword = confirmPassword,
            emailError = errorDescription
        )
        if (validationResult != null) {
            Toast.makeText(context, errorDescription!!, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "пароли совпадают", Toast.LENGTH_SHORT).show()
        }
    }

    fun onDateChange(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        val formattedDate = dateFormat.format(calendar.time)
        _registrationContent.update { currentState ->
            currentState.copy(birthday = formattedDate)
        }
    }

    private fun getErrorDescription(validationErrorType: ValidationErrorType?): Int? {
        if (validationErrorType == null) {
            return null
        }
        return ErrorTypeToStringResource.errors[validationErrorType]
    }
}