package com.example.kinoteka.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.databinding.SignInScreenBinding
import com.example.kinoteka.presentation.factory.LoginViewModelFactory
import com.example.kinoteka.presentation.factory.RegistrationViewModelFactory
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.example.kinoteka.presentation.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch

class SignInScreen : Fragment(R.layout.sign_in_screen) {
    private lateinit var viewModel: LoginViewModel
    private var binding: SignInScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignInScreenBinding.bind(view)
        val inputFields = listOf(
            binding!!.loginEditText,
            binding!!.passwordEditText
        )
        viewModel = createViewModel()
        binding?.backButton?.setOnClickListener { goBack() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginContent.collect { state ->
                binding?.loginEditText?.setText(state.login)
                binding?.passwordEditText?.setText(state.password)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isFormValid.collect { isValid ->
                binding?.signInButton?.isEnabled = isValid
                binding?.signInButton?.setBackgroundResource(
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
                        R.id.loginEditText -> viewModel.onLoginChanged(text, requireContext())
                        R.id.passwordEditText -> viewModel.onPasswordChanged(text, requireContext())
                    }
                }
            }
        }

        binding?.signInButton?.setOnClickListener {
            viewModel.onLogin()
            val intent = Intent(requireContext(), MovieActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }

    private fun createViewModel(): LoginViewModel {
        val factory = LoginViewModelFactory(requireContext())
        return ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }
}