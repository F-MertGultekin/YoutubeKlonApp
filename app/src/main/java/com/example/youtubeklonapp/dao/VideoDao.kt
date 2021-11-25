package com.example.youtubeklonapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.youtubeklonapp.entitiy.VideoEntitiy

@Dao
interface VideoDao {

    @Insert
    fun insert(videoEntitiy: VideoEntitiy)

    @Delete
    fun delete(videoEntitiy: VideoEntitiy)

    @Query("SELECT*FROM video_entity")
    fun getAllStudents(): LiveData<List<VideoEntitiy>>

    @Query("DELETE FROM video_entity")
    fun deleteAll()

    @Query("SELECT*FROM video_entity WHERE VIDEO_ID = :videoId")
    fun getVideoById(videoId : String) : LiveData<VideoEntitiy?>?

}