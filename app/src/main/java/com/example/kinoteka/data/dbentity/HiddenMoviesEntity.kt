package com.example.kinoteka.data.dbentity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "movie", foreignKeys = [
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId")
    )
])
data class HiddenMoviesEntity(
    @PrimaryKey()
    val movieId: String,
    val userId: String,
    val title: String
)
