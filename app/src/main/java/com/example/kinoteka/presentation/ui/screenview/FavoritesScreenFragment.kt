package com.example.kinoteka.presentation.ui.screenview

import FavoritesScreen
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.databinding.FavoritesScreenBinding
import com.example.kinoteka.presentation.factory.FavoriteViewModelFactory
import com.example.kinoteka.presentation.viewmodel.FavoritesViewModel

class FavoritesScreenFragment : Fragment(R.layout.favorites_screen) {
    private var binding: FavoritesScreenBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavoritesScreenBinding.bind(view)
        val viewModel: FavoritesViewModel = createViewModel()
        viewModel.navigateToSignInScreen.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToSignInScreen()
                viewModel.onNavigatedToSignInScreen()
            }
        }

        binding?.favoritesCompose?.setContent {
            binding?.favoritesCompose?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.app_background))
            FavoritesScreen(viewModel, this@FavoritesScreenFragment, ::onMovieClicked)
        }
    }

    private fun navigateToSignInScreen() {
        (activity as? MovieActivity)?.hideBottomNavigation()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, WelcomeScreen())
            .addToBackStack("MovieScreen")
            .commit()

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, SignInScreen())
            .addToBackStack(null)
            .commit()
    }


    private fun createViewModel(): FavoritesViewModel {
        val tokenDataSource = TokenDataSource(requireContext())
        val apiService = RetrofitApiClient.createFavouritesApi(tokenDataSource)
        val factory = FavoriteViewModelFactory(
            favoritesApiService = apiService
        )
        return ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
    }

    private fun onMovieClicked(movieId: String) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java).apply {
            putExtra("MOVIE_ID", movieId)
        }
        startActivity(intent)
    }
}