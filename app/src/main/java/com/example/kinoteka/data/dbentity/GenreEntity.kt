package com.example.kinoteka.data.dbentity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "genre", foreignKeys = [
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId")
    )
])

data class GenreEntity(
    @PrimaryKey()
    val genreId: String,
    val userId: String,
    val name: String
)
