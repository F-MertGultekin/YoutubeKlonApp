package com.example.youtubeklonapp.repository


import com.example.youtubeklonapp.entitiy.VideoEntity

interface VideoRepository {

    fun insertVideo(videoEntitiy: VideoEntity)

    fun deleteVideo(videoEntitiy: VideoEntity)

    fun getVideos(): List<VideoEntity>

    fun getVideoById(videoId: String?) : VideoEntity

    fun exists(videoId: String?): Boolean

    fun updateVideo(videoId : String?, isFavorite : String)
}