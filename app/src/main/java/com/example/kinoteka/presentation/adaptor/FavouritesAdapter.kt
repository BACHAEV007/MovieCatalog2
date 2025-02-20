package com.example.kinoteka.presentation.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FavouriteItemBinding
import com.example.kinoteka.presentation.model.FavouriteContent
import com.squareup.picasso.Picasso

class FavouritesAdapter(
    private val onMovieClick: (String) -> Unit
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesHolder>(){
    val moviesList = ArrayList<FavouriteContent>()
    inner class FavouritesHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FavouriteItemBinding.bind(view)
        init {
            itemView.setOnClickListener {
                val movieId = moviesList[adapterPosition].id
                onMovieClick(movieId)
            }
        }

        fun bind(movie: FavouriteContent) = with(binding){
            val posterUrl = movie.poster
            if (!posterUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(posterUrl)
                    .into(favouritePoster)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_item, parent, false)
        return FavouritesHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}