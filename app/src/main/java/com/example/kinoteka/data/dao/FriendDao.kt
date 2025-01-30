package com.example.kinoteka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.kinoteka.data.dbentity.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Upsert
    suspend fun addFriend(friend: FriendEntity)

    @Delete
    suspend fun deleteFriend(friend: FriendEntity)

    @Query("SELECT * FROM friend")
    fun fetchFriend(): Flow<List<FriendEntity>>
}