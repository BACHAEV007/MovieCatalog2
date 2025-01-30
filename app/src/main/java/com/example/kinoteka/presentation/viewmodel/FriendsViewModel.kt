package com.example.kinoteka.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinoteka.data.mapper.DatabaseMapper
import com.example.kinoteka.domain.usecase.DeleteFriendUseCase
import com.example.kinoteka.domain.usecase.FetchFriendsUseCase
import com.example.kinoteka.presentation.mapper.EntityMapper
import com.example.kinoteka.presentation.model.FriendContent
import com.example.kinoteka.presentation.model.GenreContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val entityMapper: EntityMapper,
    private val fetchFriendsUseCase: FetchFriendsUseCase,
    private val deleteFriendUseCase: DeleteFriendUseCase
) : ViewModel(){
    val friendsContent: Flow<List<FriendContent>> = fetchFriendsUseCase()
        .map {  friendModelList ->
            friendModelList.map { entityMapper.mapToContent(it) }
        }

    fun deleteFriend(friend: FriendContent) {
        viewModelScope.launch {
            deleteFriendUseCase(entityMapper.mapToDbModel(friend))
        }
    }
}