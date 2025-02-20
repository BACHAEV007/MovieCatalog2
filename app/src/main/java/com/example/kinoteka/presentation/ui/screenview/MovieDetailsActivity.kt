package com.example.kinoteka.presentation.ui.screenview

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.ui.screencompose.moviedetails.MoviesDetailsScreen
import com.example.kinoteka.presentation.factory.MovieDetailsViewModelFactory
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel



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
        viewModel.navigateToSignInScreen.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToSignInScreen()
                viewModel.onNavigatedToSignInScreen()
            }
        }
        viewModel.loadMovieDetails(movieId)
        composeView.setContent {
            composeView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_background))
            MoviesDetailsScreen(viewModel, this@MovieDetailsActivity)
        }
    }
    private fun navigateToSignInScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun createViewModel(): MovieDetailsViewModel {
        val tokenDataSource = TokenDataSource(this)
        val networkMapper = NetworkMapper()
        val entityMapper = EntityMapper()
        val movieApiService = RetrofitApiClient.createMovieApi(tokenDataSource)
        val apiService = RetrofitApiClient.createFavouritesApi(tokenDataSource)
        val profileService = RetrofitApiClient.createProfileApi(tokenDataSource)
        val movieToUIContentMapper = MoviesMapper()
        val factory = MovieDetailsViewModelFactory(
            favoritesApiService = apiService,
            movieApiService = movieApiService,
            profileApiService = profileService,
            networkMapper = networkMapper,
            contentMapper = movieToUIContentMapper,
            entityMapper
        )
        return ViewModelProvider(this, factory)[MovieDetailsViewModel::class.java]
    }
}