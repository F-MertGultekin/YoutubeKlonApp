package com.example.youtubeklonapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtubeklonapp.dao.VideoDao
import com.example.youtubeklonapp.entitiy.VideoEntitiy
import kotlinx.coroutines.flow.Flow

class VideoRepository(private val videoDao: VideoDao) {

    val allFavoriteVideos: LiveData<List<VideoEntitiy>> = videoDao.getAllStudents()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(videoEntitiy: VideoEntitiy) {
        videoDao.insert(videoEntitiy)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(videoEntitiy: VideoEntitiy) {
        videoDao.delete(videoEntitiy)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllStudents() : LiveData<List<VideoEntitiy>>  {
        return videoDao.getAllStudents()
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
         videoDao.deleteAll()
    }

    fun getVideoById(videoId : String) : LiveData<VideoEntitiy?>? {
       return videoDao.getVideoById(videoId)
    }
}