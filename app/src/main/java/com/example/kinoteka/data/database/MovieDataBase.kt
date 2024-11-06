package com.example.kinoteka.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinoteka.data.dao.FriendDao
import com.example.kinoteka.data.dao.GenreDao
import com.example.kinoteka.data.dao.MovieDao
import com.example.kinoteka.data.dao.UserDao
import com.example.kinoteka.data.dbentity.FriendEntity
import com.example.kinoteka.data.dbentity.GenreEntity
import com.example.kinoteka.data.dbentity.HiddenMoviesEntity
import com.example.kinoteka.data.dbentity.UserEntity

@Database(entities = [UserEntity::class, FriendEntity::class, GenreEntity::class, HiddenMoviesEntity::class],
    version = 1)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun FriendDao(): FriendDao
    abstract fun GenreDao(): GenreDao
    abstract fun MovieDao(): MovieDao
    abstract fun UserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: MovieDataBase? = null
        fun getDatabase(context: Context): MovieDataBase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    "movie_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}