package com.example.youtubeklonapp.service

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


    fun getDataService(part: String, order : String, maxResult : String, type : String, key : String): Single<Videos> {
        return api.getVideosData(part, order, maxResult, type, key)
    }//Mutable list mi döndürcez
}