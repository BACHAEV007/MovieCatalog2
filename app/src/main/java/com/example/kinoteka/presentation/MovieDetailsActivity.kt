package com.example.kinoteka.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.factory.MovieDetailsViewModelFactory
import com.example.kinoteka.presentation.factory.MoviesViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel



class MovieDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details_screen)
        val movieId = intent.getStringExtra("MOVIE_ID") ?: run {
            finish()
            return
        }
        val composeView = findViewById<ComposeView>(R.id.movie_details_compose)
        val viewModel: MovieDetailsViewModel = createViewModel()
        viewModel.loadMovieDetails(movieId)
        composeView.setContent {
            composeView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_background))
            MoviesDetailsScreen(viewModel, this@MovieDetailsActivity)
        }
    }

    private fun createViewModel(): MovieDetailsViewModel {
        val tokenDataSource = TokenDataSource(this)
        val networkMapper = NetworkMapper()
        val movieApiService = RetrofitApiClient.createMovieApi(tokenDataSource)
        val apiService = RetrofitApiClient.createFavouritesApi(tokenDataSource)
        val movieToUIContentMapper = MoviesMapper()
        val factory = MovieDetailsViewModelFactory(
            favoritesApiService = apiService,
            movieApiService = movieApiService,
            networkMapper = networkMapper,
            contentMapper = movieToUIContentMapper
        )
        return ViewModelProvider(this, factory)[MovieDetailsViewModel::class.java]
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}