package com.example.kinoteka.presentation

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinoteka.R
import com.example.kinoteka.databinding.MoviesScreenBinding
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.presentation.adaptor.AllMoviesAdapter
import com.example.kinoteka.presentation.adaptor.CarouselAdapter
import com.example.kinoteka.presentation.adaptor.FavouritesAdapter
import com.example.kinoteka.presentation.factory.MoviesViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesMapper
import kotlinx.coroutines.launch

class MoviesScreen : Fragment(R.layout.movies_screen) {
    private var binding: MoviesScreenBinding? = null
    private lateinit var viewModel: MoviesViewModel
    private val carouselAdapter = CarouselAdapter()
    private val allMoviesAdapter = AllMoviesAdapter()
    private val favouritesAdapter = FavouritesAdapter()
    private var currentAnimator: ValueAnimator? = null
    private var lastVisiblePosition = -1
    private var currentPage = 1
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MoviesScreenBinding.bind(view)
        setupAllMoviesRecyclerView()
        setupRecyclerCarouselView()
        setupFavouriteRecyclerView()
        viewModel = createViewModel()
        viewModel.fetchMovies(page = currentPage)
        viewModel.fetchFavourites()
        observeMovieContent()
        binding?.randomButton?.setOnClickListener {
            val randomMovieId = viewModel.getRandomMovie()
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java).apply {
                putExtra("MOVIE_ID", randomMovieId)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        currentAnimator?.cancel()
    }
    private fun createViewModel(): MoviesViewModel {
        val tokenDataSource = TokenDataSource(requireContext())
        val networkMapper = NetworkMapper()
        val movieApiService = RetrofitApiClient.createMovieApi(tokenDataSource)
        val apiService = RetrofitApiClient.createFavouritesApi(tokenDataSource)
        val movieToUIContentMapper = MoviesMapper()
        val factory = MoviesViewModelFactory(
            favoritesApiService = apiService,
            movieApiService = movieApiService,
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

        lifecycleScope.launch {
            viewModel.gridMoviesContent.collect { movieList ->
                allMoviesAdapter.moviesList.clear()
                isLoading = false
                allMoviesAdapter.moviesList.addAll(movieList)
                allMoviesAdapter.notifyDataSetChanged()
            }
        }

        lifecycleScope.launch {
            viewModel.favouritesContent.collect { movieList ->
                isLoading = false
                favouritesAdapter.moviesList.clear()
                favouritesAdapter.moviesList.addAll(movieList)
                favouritesAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun setupFavouriteRecyclerView() {
        val favouriteManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.favouritesRecycler?.apply {
            layoutManager = favouriteManager
            adapter = favouritesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val firstVisibleItemPosition = favouriteManager.findFirstVisibleItemPosition()
                    val lastVisibleItemPosition = favouriteManager.findLastVisibleItemPosition()

                    for (i in firstVisibleItemPosition..lastVisibleItemPosition) {
                        val view = favouriteManager.findViewByPosition(i) ?: continue
                        val cardView = view.findViewById<CardView>(R.id.favourite_card_view)
                        val positionInMiddle = getMiddleItemPosition(favouriteManager, recyclerView, view)
                        val scale = if (positionInMiddle == i) 1.1f else 1.0f
                        scaleView(cardView, scale)
                    }
                }
            })
        }
    }
    private fun getMiddleItemPosition(
        layoutManager: LinearLayoutManager,
        recyclerView: RecyclerView,
        view: View
    ): Int {
        val recyclerCenterX = (recyclerView.width / 2)
        val viewCenterX = (view.left + view.right) / 2
        val distanceFromCenter = Math.abs(recyclerCenterX - viewCenterX)
        return if (distanceFromCenter < recyclerView.width / 4) layoutManager.getPosition(view) else -1
    }
    private fun scaleView(view: View, scale: Float) {
        view.scaleX = scale
        view.scaleY = scale
    }
    private fun setupAllMoviesRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding?.gridRecycler?.apply {
            layoutManager = gridLayoutManager
            adapter = allMoviesAdapter
//            isNestedScrollingEnabled = false
//            binding?.gridRecycler?.overScrollMode = View.OVER_SCROLL_NEVER
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()

                    if (lastVisibleItemPosition >= totalItemCount - 5) {
                        loadNextPage()
                    }
                }
            })
        }
        binding?.scrollView?.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                val recyclerView = binding?.gridRecycler
                val gridLayoutManager = recyclerView?.layoutManager as? GridLayoutManager

                val lastVisibleItemPosition = gridLayoutManager?.findLastVisibleItemPosition() ?: 0
                val totalItemCount = gridLayoutManager?.itemCount ?: 0

                if (lastVisibleItemPosition >= totalItemCount - 5 && !isLoading) {
                    loadNextPage()
                }
            }
        }
    }
    private fun loadNextPage() {
        if (currentPage <= 5) {
            isLoading = true
            currentPage++
            viewModel.fetchMovies(page = currentPage)
//            binding?.gridRecycler?.post {
//                binding?.gridRecycler?.smoothScrollToPosition(allMoviesAdapter.itemCount - 1)
//            }
        }
    }
    private fun setupRecyclerCarouselView() {
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
            duration = 5000L
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