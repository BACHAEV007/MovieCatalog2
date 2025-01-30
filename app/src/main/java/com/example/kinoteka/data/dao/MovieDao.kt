package com.example.kinoteka.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.kinoteka.data.dbentity.HiddenMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun addMovie(movie: HiddenMoviesEntity)

    @Delete
    suspend fun deleteMovie(movie: HiddenMoviesEntity)

    @Query("SELECT * FROM movie")
    fun fetchMovie(): Flow<List<HiddenMoviesEntity>>
}