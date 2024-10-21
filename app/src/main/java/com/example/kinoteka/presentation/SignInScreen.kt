package com.example.kinoteka.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kinoteka.R
import com.example.kinoteka.databinding.SignInScreenBinding

class SignInScreen : Fragment(R.layout.sign_in_screen) {
    private var binding: SignInScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignInScreenBinding.bind(view)

        binding?.backButton?.setOnClickListener { goBack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun goBack() {
        parentFragmentManager.popBackStack()
    }
}