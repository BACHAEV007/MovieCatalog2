package com.example.kinoteka.presentation.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinoteka.databinding.FeedItemBinding
import com.example.kinoteka.presentation.model.MovieContent

class FeedAdapter (val context : Context, var list : List<MovieContent>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    inner class FeedViewHolder(val binding : FeedItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {


        return FeedViewHolder(FeedItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        Glide.with(context).load(list[position].poster).into(holder.binding.posterImageView)
    }

    fun setData(newList: List<MovieContent>) {
        list = newList
        notifyDataSetChanged()
    }

}