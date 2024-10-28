package com.example.kinoteka.presentation

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.R
import com.example.kinoteka.databinding.AllMoviesGridItemBinding
import com.example.kinoteka.databinding.MovieItemCarouselBinding
import com.example.kinoteka.presentation.model.MovieContent
import com.squareup.picasso.Picasso

class AllMoviesAdapter : RecyclerView.Adapter<AllMoviesAdapter.AllMoviesHolder>(){
    val moviesList = ArrayList<MovieContent>()
    inner class AllMoviesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = AllMoviesGridItemBinding.bind(view)
        init {
            //Настраиваем listener
        }
        fun bind(movie: MovieContent) = with(binding){
            val posterUrl = movie.poster
            if (!posterUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(posterUrl)
                    .into(allMoviesGreedPoster)
            }
            val rating = movie.rating
            ratingText.text = rating.toString()

            val backgroundColor = when {
                rating!! < 2 -> ContextCompat.getColor(itemView.context, R.color.marks_one)
                rating < 3 -> ContextCompat.getColor(itemView.context, R.color.marks_two)
                rating < 4 -> ContextCompat.getColor(itemView.context, R.color.marks_three)
                rating < 5 -> ContextCompat.getColor(itemView.context, R.color.marks_four)
                rating < 6 -> ContextCompat.getColor(itemView.context, R.color.marks_five)
                rating < 7 -> ContextCompat.getColor(itemView.context, R.color.marks_six)
                rating < 8 -> ContextCompat.getColor(itemView.context, R.color.marks_seven)
                rating < 9 -> ContextCompat.getColor(itemView.context, R.color.marks_eight)
                else -> ContextCompat.getColor(itemView.context, R.color.marks_nine)
            }
            val backgroundDrawable = ratingText.background as? GradientDrawable
            backgroundDrawable?.setColor(backgroundColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_movies_grid_item, parent, false)
        return AllMoviesHolder(view)
    }

    override fun onBindViewHolder(holder: AllMoviesHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}