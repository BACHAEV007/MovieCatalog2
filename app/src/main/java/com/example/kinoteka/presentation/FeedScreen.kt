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
import com.example.kinoteka.presentation.factory.FeedViewModelFactory
import com.example.kinoteka.presentation.factory.LoginViewModelFactory
import com.example.kinoteka.presentation.viewmodel.FeedViewModel
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class FeedScreen : Fragment(R.layout.feed_screen) {
    private lateinit var viewModel: FeedViewModel
    private var binding: FeedScreenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FeedScreenBinding.bind(view)
        viewModel = createViewModel()
        viewModel.fetchMovies(page = (1..5).random())
        observeMovieContent()
    }

    private fun createViewModel(): FeedViewModel {
        val factory = FeedViewModelFactory(requireContext())
        return ViewModelProvider(this, factory)[FeedViewModel::class.java]
    }

    private fun observeMovieContent() {
        lifecycleScope.launch {
            viewModel.movieContent.collect { movieContent ->
                binding?.apply {
                    movieTitleTextView.text = movieContent.name
                    countryYearTextView.text = "${movieContent.country} • ${movieContent.year}"

                    val genres = movieContent.genres.map { it.name }
                    firstGenre.text = genres.getOrNull(0) ?: ""
                    secondGenre.text = genres.getOrNull(1) ?: ""
                    thirdGenre.text = genres.getOrNull(2) ?: ""
                    secondGenre.visibility = if (genres.size > 1) View.VISIBLE else View.GONE
                    thirdGenre.visibility = if (genres.size > 2) View.VISIBLE else View.GONE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(genreContainer)
                    if (genres.size == 1) {
                        constraintSet.connect(
                            R.id.firstGenre,
                            ConstraintSet.START,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.START
                        )
                        constraintSet.connect(
                            R.id.firstGenre,
                            ConstraintSet.END,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.END
                        )
                    } else {
                        constraintSet.connect(
                            R.id.firstGenre,
                            ConstraintSet.START,
                            ConstraintSet.PARENT_ID,
                            ConstraintSet.START
                        )
                        constraintSet.clear(R.id.firstGenre, ConstraintSet.END)
                    }
                    constraintSet.applyTo(genreContainer)

                    val posterUrl = movieContent.poster
                    if (!posterUrl.isNullOrEmpty()) {
                        Picasso.get()
                            .load(posterUrl)
                            .into(posterImageView)
                    } //else {
//                        posterImageView.setImageResource(R.drawable.ic_movies)
//                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getRandomMovie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}