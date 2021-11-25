package com.example.youtubeklonapp

import com.example.youtubeklonapp.database.VideoDatabase
import com.example.youtubeklonapp.repository.VideoRepository
import android.app.Application

open class YoutubeKlonApplication : Application()
 {
        val database by lazy { VideoDatabase.getDatabase(this) }
        val repository by lazy { VideoRepository(database.videoDao()) }
 }
