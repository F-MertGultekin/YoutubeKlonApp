package com.example.youtubeklonapp.service

import com.example.youtubeklonapp.model.Videos
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
interface YoutubeApi
{
    @GET("search")
     fun getVideosData(

        @Query("part") part: String,
        @Query("order") order: String,
        @Query("maxResults") maxResults: String,
        @Query("type") type: String,
        @Query("key") key: String
    ) : Single<Videos> //?????????????
}