package com.example.kinoteka.presentation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoteka.R
import com.example.kinoteka.databinding.MoviesScreenBinding
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.factory.MoviesViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesToUIContentMapper
import kotlinx.coroutines.launch

class MoviesScreen : Fragment(R.layout.movies_screen) {
    private var binding: MoviesScreenBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private val carouselAdapter = CarouselAdapter()
    private var currentAnimator: ValueAnimator? = null
    private var lastVisiblePosition = -1

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
        currentAnimator?.cancel()
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
    private fun observeMovieContent() {
        lifecycleScope.launch {
            viewModel.carouselContent.collect { movieList ->
                carouselAdapter.moviesList.clear()
                carouselAdapter.moviesList.addAll(movieList)
                carouselAdapter.notifyDataSetChanged()

            }
        }
    }
    private fun setupRecyclerView() {
        val carouselManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.recycler?.apply {
            layoutManager = carouselManager
            adapter = carouselAdapter

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val firstVisibleItemPosition = carouselManager.findFirstVisibleItemPosition()

                    if (firstVisibleItemPosition < lastVisiblePosition) {
                        resetProgressBars()
                    }
                    updateProgressBars(firstVisibleItemPosition)
                    lastVisiblePosition = firstVisibleItemPosition
                }
            })
        }
    }

    private fun resetProgressBars() {
        val progressBars = listOf(
            binding?.progressBar1,
            binding?.progressBar2,
            binding?.progressBar3,
            binding?.progressBar4,
            binding?.progressBar5
        )

        progressBars.forEach { progressBar ->
            progressBar?.progress = 0
        }

        currentAnimator?.cancel()
        currentAnimator = null
    }

    private fun updateProgressBars(currentPosition: Int) {
        val progressBars = listOf(
            binding?.progressBar1,
            binding?.progressBar2,
            binding?.progressBar3,
            binding?.progressBar4,
            binding?.progressBar5
        )

        progressBars.forEachIndexed { index, progressBar ->
            progressBar?.progress = if (index < currentPosition) 100 else 0
        }

        if (currentPosition < progressBars.size && progressBars[currentPosition]?.progress != 100) {
            animateSingleProgressBar(progressBars, currentPosition)
        }
    }

    private fun animateSingleProgressBar(progressBars: List<ProgressBar?>, index: Int) {
        val progressBar = progressBars.getOrNull(index) ?: return

        currentAnimator?.cancel()
        currentAnimator = ValueAnimator.ofInt(0, 100).apply {
            duration = 5000L // 5 секунд для заполнения
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                progressBar.progress = animation.animatedValue as Int
                if (progressBar.progress == 100) {
                    scrollToNextItem(index)
                }
            }
            doOnEnd {
                if (index == progressBars.size - 1) {
                    resetProgressBars()
                    binding?.recycler?.smoothScrollToPosition(0)
                    updateProgressBars(0)
                }
            }
        }

        currentAnimator?.start()
    }

    private fun scrollToNextItem(currentIndex: Int) {
        val nextIndex = currentIndex + 1
        val layoutManager = binding?.recycler?.layoutManager as? LinearLayoutManager
        if (nextIndex < carouselAdapter.itemCount) {
            layoutManager?.smoothScrollToPosition(binding?.recycler, RecyclerView.State(), nextIndex)
            val centerView = layoutManager?.findViewByPosition(nextIndex)
            if (centerView != null) {
                binding?.recycler?.smoothScrollToPosition(nextIndex)
            }
        }
    }
}