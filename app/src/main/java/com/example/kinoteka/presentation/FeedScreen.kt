package com.example.kinoteka.presentation

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FeedScreenBinding
import com.example.kinoteka.presentation.adaptor.FeedAdapter
import com.example.kinoteka.presentation.factory.FeedViewModelFactory
import com.example.kinoteka.presentation.factory.LoginViewModelFactory
import com.example.kinoteka.presentation.viewmodel.FeedViewModel
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.launch

class FeedScreen : Fragment(R.layout.feed_screen) {
    private lateinit var viewModel: FeedViewModel
    private var binding: FeedScreenBinding? = null
    private lateinit var cardStackAdapter: FeedAdapter
    private var currPosition: Int = 0
    private lateinit var cardStackLayoutManager: CardStackLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FeedScreenBinding.bind(view)
        viewModel = createViewModel()
        setupCardStackView()


        viewModel.loadNewMovies()
        observeMovieContent()
        observeMovies()
    }

    private fun createViewModel(): FeedViewModel {
        val factory = FeedViewModelFactory(requireContext())
        return ViewModelProvider(this, factory)[FeedViewModel::class.java]
    }

    private fun observeMovieContent() {
        lifecycleScope.launch {
            viewModel.movieContent.collect { movieContent ->
                if (movieContent.isNotEmpty()) {
                    binding?.apply {
                        movieTitleTextView.text = movieContent[0].name
                        countryYearTextView.text = "${movieContent[0].country} • ${movieContent[0].year}"

                        val genres = movieContent[0].genres.map { it.name }
                        firstGenre.text = genres.getOrNull(0) ?: ""
                        secondGenre.text = genres.getOrNull(1) ?: ""
                        thirdGenre.text = genres.getOrNull(2) ?: ""
                        firstGenre.visibility = if (genres.size == 1) View.VISIBLE else View.GONE
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
                    }
                } else {
                    binding?.apply {
                        movieTitleTextView.text = "Название"
                        countryYearTextView.text = "Страна • Год"
                        firstGenre.visibility = View.GONE
                        secondGenre.visibility = View.GONE
                        thirdGenre.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setupCardStackView() {
        cardStackLayoutManager = CardStackLayoutManager(requireContext(), object :
            CardStackListener {
            override fun onCardSwiped(direction: Direction?) {
                viewModel.removeMovie()
                if (viewModel.movieContent.value.size <= 0) {
                    viewModel.loadNewMovies()
                }
            }
            override fun onCardDragging(direction: Direction, ratio: Float) {
                if (currPosition < 0 || currPosition >= binding?.cardImageView?.adapter?.itemCount ?: 0) {
                    return
                }

                val cardView = binding?.cardImageView?.layoutManager?.findViewByPosition(currPosition)

                val alpha = (ratio * 200).toInt().coerceIn(0, 200)

                val poster = cardView?.findViewById<ImageView>(R.id.posterImageView)
                val icon = cardView?.findViewById<ImageView>(R.id.icon)

                when (direction) {
                    Direction.Left -> {
                        poster?.setColorFilter(Color.argb(alpha, 51, 51, 51))
                        icon?.setImageResource(R.drawable.not_liked_feed)
                    }
                    Direction.Right -> {
                        val gradientDrawable = GradientDrawable(
                            GradientDrawable.Orientation.LEFT_RIGHT,
                            intArrayOf(Color.argb(alpha, 223, 40, 0), Color.argb(alpha, 255, 102, 51))
                        )
                        poster?.foreground = gradientDrawable
                        icon?.setImageResource(R.drawable.liked_feed)
                    }
                    else -> {
                        poster?.clearColorFilter()
                        poster?.foreground = null
                        icon?.setImageDrawable(null)
                    }
                }
            }
            override fun onCardRewound() {
                resetCardView(currPosition)
            }
            override fun onCardCanceled() {
                resetCardView(currPosition)
            }
            override fun onCardAppeared(view: View?, position: Int) {
                currPosition = position
                resetCardView(currPosition)
            }
            override fun onCardDisappeared(view: View?, position: Int) {
                resetCardView(currPosition)
            }
        })

        cardStackAdapter = FeedAdapter(requireContext(), emptyList())
        binding?.cardImageView?.layoutManager = cardStackLayoutManager
        binding?.cardImageView?.adapter = cardStackAdapter
    }

    private fun resetCardView(position: Int) {
        if (position < 0 || position >= binding?.cardImageView?.adapter?.itemCount ?: 0) {
            return
        }
        val cardView = binding?.cardImageView?.layoutManager?.findViewByPosition(position)
        val poster = cardView?.findViewById<ImageView>(R.id.posterImageView)
        val icon = cardView?.findViewById<ImageView>(R.id.icon)

        poster?.clearColorFilter()
        poster?.foreground = null
        icon?.setImageDrawable(null)
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            viewModel.movieContent.collect { movies ->
                cardStackAdapter.setData(movies)
                cardStackAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}