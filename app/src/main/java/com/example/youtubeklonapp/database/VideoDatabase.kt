package com.example.youtubeklonapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.youtubeklonapp.dao.VideoDao
import com.example.youtubeklonapp.entitiy.VideoEntitiy
@Database(entities = arrayOf(VideoEntitiy::class), version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun getDatabase(context: Context): VideoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}