package com.example.kinoteka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.kinoteka.data.dbentity.FriendEntity
import com.example.kinoteka.data.dbentity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Upsert
    suspend fun addGenre(genre: GenreEntity)

    @Delete
    suspend fun deleteGenre(genre: GenreEntity)

    @Query("SELECT * FROM genre")
    fun fetchGenre(): Flow<List<GenreEntity>>
}