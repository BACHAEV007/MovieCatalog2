package com.example.kinoteka.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.FavouritesApiService
import com.example.kinoteka.data.network.api.MovieApiService
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.factory.MovieDetailsViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.viewmodel.MovieDetailsViewModel


@Composable
fun MoviesDetailsScreen(movieId: String) {
    val context = LocalContext.current
    val tokenDataSource = remember { TokenDataSource(context) }
    val networkMapper = remember { NetworkMapper() }
    val movieApiService = remember { RetrofitApiClient.createMovieApi(tokenDataSource) }
    val favouritesApiService = remember { RetrofitApiClient.createFavouritesApi(tokenDataSource) }
    val contentMapper = remember { MoviesMapper() }

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    viewModelStoreOwner?.let {
        val viewModelFactory = remember {
            MovieDetailsViewModelFactory(
                favouritesApiService,
                movieApiService,
                networkMapper,
                contentMapper
            )
        }

        val movieDetailsViewModel: MovieDetailsViewModel = viewModel(
            factory = viewModelFactory,
            viewModelStoreOwner = it
        )

        movieDetailsViewModel.loadMovieDetails(movieId)
    }
}