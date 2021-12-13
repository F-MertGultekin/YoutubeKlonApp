package com.example.youtubeklonapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.youtubeklonapp.entitiy.VideoEntity

const val DATABASE_VERSION = 1

@Database(
    entities = [VideoEntity::class],
    version = DATABASE_VERSION
)
abstract class FavoriteVideosDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        private const val DATABASE_NAME = "FavoriteVideos"

        fun buildDatabase(context: Context): FavoriteVideosDatabase {
            return Room.databaseBuilder(
                context,
                FavoriteVideosDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }

    }

}



