package com.example.kinoteka.presentation

import FavoritesScreen
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.databinding.FavoritesScreenBinding
import com.example.kinoteka.databinding.FriendsSceenBinding
import com.example.kinoteka.presentation.factory.FavoriteViewModelFactory
import com.example.kinoteka.presentation.factory.MovieDetailsViewModelFactory
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel

class FavoritesScreenFragment : Fragment(R.layout.favorites_screen) {
    private var binding: FavoritesScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavoritesScreenBinding.bind(view)
        val viewModel: FavoritesViewModel = createViewModel()
        binding?.favoritesCompose?.setContent {
            binding?.favoritesCompose?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_background))
            FavoritesScreen(viewModel, this@FavoritesScreenFragment)
        }
    }

    private fun createViewModel(): FavoritesViewModel {
        val tokenDataSource = TokenDataSource(requireContext())
        val apiService = RetrofitApiClient.createFavouritesApi(tokenDataSource)
        val factory = FavoriteViewModelFactory(
            favoritesApiService = apiService
        )
        return ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
    }
}