package com.example.youtubeklonapp.repository

import com.example.youtubeklonapp.dao.VideoDao
import com.example.youtubeklonapp.entitiy.VideoEntity

class VideoRepositoryImpl(private val videoDao: VideoDao) : VideoRepository {
    override fun insertVideo(videoEntitiy: VideoEntity) =videoDao.insertVideo(videoEntitiy)



    override fun deleteVideo(videoEntitiy: VideoEntity) =videoDao.deleteVideo(videoEntitiy)

    override fun getVideos(): List<VideoEntity> = videoDao.getVideos()

    override fun getVideoById(videoId: String?) : VideoEntity = videoDao.getVideoById(videoId)

    override fun exists(videoId: String?): Boolean = videoDao.exists(videoId)
    override fun updateVideo(videoId : String?, isFavorite : String) = videoDao.updateVideo(videoId,isFavorite)

}