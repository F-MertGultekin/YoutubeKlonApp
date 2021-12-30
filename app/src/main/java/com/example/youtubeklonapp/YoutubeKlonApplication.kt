package com.example.youtubeklonapp

import android.app.Application
import com.example.youtubeklonapp.dao.FavoriteVideosDatabase
import com.example.youtubeklonapp.repository.VideoRepository
import com.example.youtubeklonapp.repository.VideoRepositoryImpl
import org.koin.android.ext.android.startKoin
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

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
     val viewModelModule = module {

         viewModel {
             VideoListViewModel()
         }
     }
     override fun onCreate() {
         super.onCreate()
         instance = this
         startKoin(this, listOf(viewModelModule))


     }
 }
