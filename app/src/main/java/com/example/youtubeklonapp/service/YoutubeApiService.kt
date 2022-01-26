package com.example.youtubeklonapp.service

import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.model.Videos
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeApiService {

    val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(YoutubeApi::class.java)


    fun getDataService(query : String?,pageToken: String?): Single<Videos> {
        return api.getVideosData("snippet","date","5","video",BuildConfig.API_KEY,query,pageToken)
    }
}