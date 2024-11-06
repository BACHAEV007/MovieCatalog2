package com.example.kinoteka.presentation.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FriendsItemBinding
import com.example.kinoteka.presentation.model.FriendContent
import com.squareup.picasso.Picasso

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsHolder>(){
    val friendsList = ArrayList<FriendContent>()
    inner class FriendsHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = FriendsItemBinding.bind(view)
        init {
            //Настраиваем listener
        }
        fun bind(friend: FriendContent) = with(binding){
            val posterUrl = friend.avatar
            if (!posterUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(posterUrl)
                    .into(friendAvatar)
            }
            val name = friend.nickName
            friendName.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friends_item, parent, false)
        return FriendsHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.bind(friendsList[position])
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }
}