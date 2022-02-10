package com.example.youtubeklonapp.repository

import com.example.youtubeklonapp.service.YoutubeApiService
import org.koin.java.KoinJavaComponent.inject


class NetworkRepository(val service: YoutubeApiService ) {

    suspend fun getDataRepository(query : String?,pageToken: String?) = service.getDataService(query,pageToken)

}
