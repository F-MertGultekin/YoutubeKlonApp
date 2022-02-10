package com.example.youtubeklonapp

import android.app.Application
import com.example.youtubeklonapp.dao.FavoriteVideosDatabase
import com.example.youtubeklonapp.repository.NetworkRepository
import com.example.youtubeklonapp.repository.VideoRepository
import com.example.youtubeklonapp.repository.VideoRepositoryImpl
import com.example.youtubeklonapp.service.YoutubeApiService
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
     val appModule = module {

         viewModel {VideoListViewModel(get())}
         single { NetworkRepository(get())}
         single { YoutubeApiService()}
     }

     //open fun provideDependency() = appComponent

     override fun onCreate() {
         super.onCreate()
         instance = this


         startKoin{
             androidLogger()
             androidContext(this@YoutubeKlonApplication)
             modules(appModule)
         }

     }
 }
