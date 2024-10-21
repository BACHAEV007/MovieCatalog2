package com.example.kinoteka.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.databinding.SignUpScreenBinding
import com.example.kinoteka.domain.usecase.RegisterUserUseCase
import com.example.kinoteka.domain.usecase.ValidateConfirmPasswordUseCase
import com.example.kinoteka.domain.usecase.ValidateEmailUseCase
import com.example.kinoteka.domain.usecase.ValidateLoginUseCase
import com.example.kinoteka.domain.usecase.ValidatePasswordUseCase
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class SignUpScreen() : Fragment(R.layout.sign_up_screen) {
    private lateinit var validateLoginUseCase: ValidateLoginUseCase
    private lateinit var validateEmailUseCase: ValidateEmailUseCase
    private lateinit var validatePasswordUseCase: ValidatePasswordUseCase
    private lateinit var validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private var binding: SignUpScreenBinding? = null
    private lateinit var viewModel: RegistrationViewModel
    var dateAndTime: Calendar = Calendar.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateLoginUseCase = ValidateLoginUseCase()
        validateEmailUseCase = ValidateEmailUseCase()
        validatePasswordUseCase = ValidatePasswordUseCase()
        validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()

        binding = SignUpScreenBinding.bind(view)
        val inputFields = listOf(
            binding!!.loginEditText,
            binding!!.passwordEditText,
            binding!!.emailEditText,
            binding!!.confirmPasswordText
        )
        viewModel = RegistrationViewModel(validateLoginUseCase, validateEmailUseCase, validatePasswordUseCase, validateConfirmPasswordUseCase)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationContent.collect { state ->
                binding?.loginEditText?.setText(state.login)
                binding?.emailEditText?.setText(state.email)
                binding?.passwordEditText?.setText(state.password)
                binding?.confirmPasswordText?.setText(state.confirmPassword)
                binding?.birthdayEditText?.setText(state.birthday)
//                binding?.passwordErrorTextView?.text = state.passwordError?.name
            }
        }

        inputFields.forEach { editText ->
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val text = editText.text.toString()
                    when (editText.id) {
                        R.id.login_edit_text -> viewModel.onLoginChanged(text, requireContext())
                        R.id.password_edit_text -> viewModel.onPasswordChanged(text, requireContext())
                        R.id.email_edit_text -> viewModel.onEmailChanged(text, requireContext())
                        R.id.confirm_password_text -> viewModel.onRepeatPasswordChanged(text, requireContext())
                    }
                }
            }
        }
        binding?.birthdayCalendar?.setOnClickListener {
            showDatePicker()
        }
        binding?.backButton?.setOnClickListener { goBack() }
    }
    private fun showDatePicker() {
        val year = dateAndTime.get(Calendar.YEAR)
        val month = dateAndTime.get(Calendar.MONTH)
        val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                dateAndTime.set(selectedYear, selectedMonth, selectedDay)
                viewModel.onDateChange(selectedYear, selectedMonth + 1, selectedDay)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}