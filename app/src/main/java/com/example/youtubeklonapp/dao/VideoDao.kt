package com.example.youtubeklonapp.dao

import androidx.room.*
import com.example.youtubeklonapp.entitiy.VideoEntity

@Dao
interface VideoDao {

    @Insert
    fun insertVideo(videoEntitiy: VideoEntity)

    @Delete
    fun deleteVideo(videoEntitiy: VideoEntity)

    @Query("SELECT*FROM video_entity")
    fun getVideos(): List<VideoEntity>

    @Query("SELECT*FROM video_entity WHERE VIDEO_ID = :videoId")
    fun getVideoById(videoId: String?) : VideoEntity

    @Query("SELECT EXISTS (SELECT 1 FROM video_entity WHERE VIDEO_ID = :videoId)")
    fun exists(videoId: String?): Boolean


    @Query("UPDATE video_entity SET IS_FAVORITE=:isFavorite WHERE VIDEO_ID = :videoId")
    fun updateVideo(videoId : String?, isFavorite : String)

}