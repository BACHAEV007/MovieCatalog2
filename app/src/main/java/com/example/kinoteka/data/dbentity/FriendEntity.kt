package com.example.kinoteka.data.dbentity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "friend", foreignKeys = [
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId")
    )
])
data class FriendEntity(
    @PrimaryKey()
    val friendId: String,
    val userId: String,
    val nickName: String,
    val avatar: String
)
