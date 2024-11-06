package com.example.kinoteka.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kinoteka.R
import com.example.kinoteka.databinding.FriendsSceenBinding
import com.example.kinoteka.presentation.adaptor.FriendsAdapter
import com.example.kinoteka.presentation.factory.FriendsViewModelFactory
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.viewmodel.FriendsViewModel

class FriendsScreen : Fragment(R.layout.friends_sceen) {
    private lateinit var viewModel: FriendsViewModel
    private var binding: FriendsSceenBinding? = null
    private val friendAdapter = FriendsAdapter { friend ->
        viewModel.deleteFriend(friend)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FriendsSceenBinding.bind(view)
        viewModel = createViewModel()
        setupFriendRecyclerView()
        lifecycleScope.launchWhenStarted {
            viewModel.friendsContent.collect { friends ->
                friendAdapter.updateFriendsList(friends)
            }
        }
    }

    fun setupFriendRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding?.gridRecycler?.apply {
            layoutManager = gridLayoutManager
            adapter = friendAdapter
        }
    }

    private fun createViewModel(): FriendsViewModel {
        val factory = FriendsViewModelFactory()
        return ViewModelProvider(this, factory)[FriendsViewModel::class.java]
    }
}