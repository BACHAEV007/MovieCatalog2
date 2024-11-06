package com.example.kinoteka.presentation

import FavoritesScreen
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FavoritesScreenBinding
import com.example.kinoteka.databinding.FriendsSceenBinding
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel

class FavoritesScreenFragment : Fragment(R.layout.favorites_screen) {
    private var binding: FavoritesScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavoritesScreenBinding.bind(view)
        //val viewModel: MovieDetailsViewModel = createViewModel()
        binding?.favoritesCompose?.setContent {
            binding?.favoritesCompose?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_background))
            FavoritesScreen(this@FavoritesScreenFragment)
        }
    }
}