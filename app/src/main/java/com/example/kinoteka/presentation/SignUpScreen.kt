package com.example.kinoteka.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.databinding.SignUpScreenBinding
import com.example.kinoteka.domain.model.Gender
import com.example.kinoteka.presentation.factory.RegistrationViewModelFactory
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class SignUpScreen() : Fragment(R.layout.sign_up_screen) {
    private var binding: SignUpScreenBinding? = null
    private lateinit var viewModel: RegistrationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = SignUpScreenBinding.bind(view)
        binding?.maleButton?.isSelected = true
        val inputFields = listOf(
            binding!!.loginEditText,
            binding!!.passwordEditText,
            binding!!.emailEditText,
            binding!!.confirmPasswordText,
            binding!!.nameEditText
        )
        viewModel = createViewModel()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationContent.collect { state ->
                binding?.loginEditText?.setText(state.login)
                binding?.emailEditText?.setText(state.email)
                binding?.passwordEditText?.setText(state.password)
                binding?.confirmPasswordText?.setText(state.confirmPassword)
                binding?.birthdayEditText?.setText(state.birthday)
                binding?.nameEditText?.setText(state.name)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFormValid.collect { isValid ->
                binding?.signUpButton?.isEnabled = isValid
                binding?.signUpButton?.setBackgroundResource(
                    if (isValid) R.drawable.sign_in_button
                    else R.drawable.sign_up_button
                )
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
                        R.id.name_edit_text -> viewModel.onNameChanged(text, requireContext())
                    }
                }
            }
        }
        binding?.birthdayCalendar?.setOnClickListener {
            showDatePicker()
        }
        binding?.maleButton?.setOnClickListener {
            binding?.maleButton?.isSelected = true
            binding?.femaleButton?.isSelected = false
            viewModel.onGenderChange(Gender.MALE.ordinal)
        }

        binding?.signUpButton?.setOnClickListener{
            viewModel.onRegister()
        }

        binding?.femaleButton?.setOnClickListener {
            binding?.maleButton?.isSelected = false
            binding?.femaleButton?.isSelected = true
            viewModel.onGenderChange(Gender.FEMALE.ordinal)
        }
        binding?.backButton?.setOnClickListener { goBack() }
    }
    private fun showDatePicker() {
        val dateAndTime: Calendar = Calendar.getInstance()
        val year = dateAndTime.get(Calendar.YEAR)
        val month = dateAndTime.get(Calendar.MONTH)
        val day = dateAndTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                dateAndTime.set(selectedYear, selectedMonth, selectedDay)
                viewModel.onDateChange(selectedYear, selectedMonth, selectedDay)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun createViewModel(): RegistrationViewModel {
        val factory = RegistrationViewModelFactory(requireContext())
        return ViewModelProvider(this, factory)[RegistrationViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}