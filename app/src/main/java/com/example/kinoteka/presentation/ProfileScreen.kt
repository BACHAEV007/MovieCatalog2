package com.example.kinoteka.presentation

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FeedScreenBinding
import com.example.kinoteka.databinding.ProfileScreenBinding
import com.example.kinoteka.presentation.factory.FeedViewModelFactory
import com.example.kinoteka.presentation.factory.LoginViewModelFactory
import com.example.kinoteka.presentation.viewmodel.FeedViewModel
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileScreen : Fragment(R.layout.profile_screen) {
    private var binding: ProfileScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileScreenBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}