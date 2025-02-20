package com.example.kinoteka.presentation.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.R
import com.example.kinoteka.databinding.MovieItemCarouselBinding
import com.example.kinoteka.presentation.model.MovieContent
import com.squareup.picasso.Picasso

class CarouselAdapter(
    private val onMovieClick: (String) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselHolder>() {
    val moviesList = ArrayList<MovieContent>()
    inner class CarouselHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = MovieItemCarouselBinding.bind(view)
        init {
            binding.button.setOnClickListener {
                val movieId = moviesList[adapterPosition].id
                onMovieClick(movieId)
            }
        }
        fun bind(movie: MovieContent) = with(binding){
            val posterUrl = movie.poster
            if (!posterUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(posterUrl)
                    .into(poster)
            }
            movieTitle.text = movie.name
            firstGenre.text = movie.genres.getOrNull(0)?.name ?: ""
            secondGenre.text = movie.genres.getOrNull(1)?.name ?: ""
            thirdGenre.text = movie.genres.getOrNull(2)?.name ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_carousel, parent, false)
        return CarouselHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}