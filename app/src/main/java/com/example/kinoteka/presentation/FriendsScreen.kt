package com.example.kinoteka.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FeedScreenBinding
import com.example.kinoteka.databinding.FriendsSceenBinding
import com.example.kinoteka.databinding.ProfileScreenBinding
import com.example.kinoteka.presentation.adaptor.AllMoviesAdapter
import com.example.kinoteka.presentation.adaptor.FriendsAdapter
import com.example.kinoteka.presentation.viewmodel.FeedViewModel

class FriendsScreen : Fragment(R.layout.friends_sceen) {
    private lateinit var viewModel: FeedViewModel
    private var binding: FriendsSceenBinding? = null
    private val friendAdapter = FriendsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FriendsSceenBinding.bind(view)
        setupFriendRecyclerView()
    }
    fun setupFriendRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding?.gridRecycler?.apply {
            layoutManager = gridLayoutManager
            adapter = friendAdapter
        }
    }
}