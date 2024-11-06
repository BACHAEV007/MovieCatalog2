package com.example.kinoteka.data.dbentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey()
    val userId: String,
    val nickName: String,
    val avatar: String
)
