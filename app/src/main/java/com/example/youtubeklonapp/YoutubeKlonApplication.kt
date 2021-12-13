package com.example.youtubeklonapp

import android.app.Application
import com.example.youtubeklonapp.dao.FavoriteVideosDatabase
import com.example.youtubeklonapp.repository.VideoRepository
import com.example.youtubeklonapp.repository.VideoRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class YoutubeKlonApplication : Application()
 {
     companion object {
         private lateinit var instance: YoutubeKlonApplication

         private val database: FavoriteVideosDatabase by lazy {
             FavoriteVideosDatabase.buildDatabase(instance)
         }

         val repository: VideoRepository by lazy {
             VideoRepositoryImpl(
                 database.videoDao()
             )
         }
     }
     override fun onCreate() {
         super.onCreate()
         instance = this
     }
 }
