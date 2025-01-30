package com.example.kinoteka.presentation.ui.screenview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.kinoteka.R
import com.example.kinoteka.databinding.WelcomeScreenBinding

class WelcomeScreen : Fragment(R.layout.welcome_screen) {
    private var binding: WelcomeScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = WelcomeScreenBinding.bind(view)
        
        binding?.signInButton?.setOnClickListener{ toSignInScreen() }
        binding?.signUpButton?.setOnClickListener{ toSignUpScreen() }
    }

    private fun toSignInScreen(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SignInScreen())
            .addToBackStack(null)
            .commit()
    }

    private fun toSignUpScreen(){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SignUpScreen())
            .addToBackStack(null)
            .commit()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}