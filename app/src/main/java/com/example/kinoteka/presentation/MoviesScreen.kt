package com.example.kinoteka.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FeedScreenBinding
import com.example.kinoteka.databinding.MoviesScreenBinding
import com.example.kinoteka.presentation.viewmodel.FeedViewModel
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.domain.usecase.GetMoviesUseCase
import com.example.kinoteka.presentation.factory.MoviesViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesToUIContentMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesScreen : Fragment(R.layout.movies_screen) {
    private var binding: MoviesScreenBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private val carouselAdapter = CarouselAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesScreenBinding.bind(view)
        setupRecyclerView()

        viewModel = createViewModel()
        viewModel.fetchMovies(page = 1)
        observeMovieContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    private fun createViewModel(): MoviesViewModel {
        val tokenDataSource = TokenDataSource(requireContext())
        val networkMapper = NetworkMapper()
        val apiService = RetrofitApiClient.createMovieApi(tokenDataSource)
        val movieToUIContentMapper = MoviesToUIContentMapper()

        val factory = MoviesViewModelFactory(
            tokenDataSource = tokenDataSource,
            apiService = apiService,
            networkMapper = networkMapper,
            movieToUIContentMapper = movieToUIContentMapper
        )
        return ViewModelProvider(this, factory)[MoviesViewModel::class.java]
    }
    private fun setupRecyclerView() {
        val carouselManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recycler?.apply {
            layoutManager = carouselManager
            adapter = carouselAdapter
        }
    }
    private fun observeMovieContent() {
        lifecycleScope.launch {
            viewModel.carouselContent.collect { movieList ->
                carouselAdapter.moviesList.clear()
                carouselAdapter.moviesList.addAll(movieList)
                carouselAdapter.notifyDataSetChanged()
                animateProgressBars()
            }
        }
    }
    private fun animateProgressBars() {
        var currentIndex = 0

        fun fillNextProgressBar() {
            if (currentIndex < carouselAdapter.moviesList.size) {
                val movie = carouselAdapter.moviesList[currentIndex]
                val totalTime = 5000
                val increment = 100
                val totalIncrements = totalTime / increment

                lifecycleScope.launch {
                    for (i in 0 until totalIncrements) {
                        delay(increment.toLong())
                        movie.progress += 1
                        carouselAdapter.notifyItemChanged(currentIndex)
                    }
                    currentIndex++
                    fillNextProgressBar()
                }
            }
        }
        fillNextProgressBar()
    }
}