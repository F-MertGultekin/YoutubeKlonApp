package com.example.youtubeklonapp.viewmodel

import androidx.lifecycle.*
import com.example.youtubeklonapp.entitiy.VideoEntitiy
import com.example.youtubeklonapp.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoPlayerViewModel(private val videoRepository: VideoRepository ): ViewModel() {


    val allVideos: LiveData<List<VideoEntitiy>> = videoRepository.allFavoriteVideos
    fun insert(videoEntitiy: VideoEntitiy) = viewModelScope.launch {
        videoRepository.insert(videoEntitiy)
    }
    fun delete(videoEntitiy: VideoEntitiy) = viewModelScope.launch {
        videoRepository.delete(videoEntitiy)
    }
    fun deleteAll() = viewModelScope.launch {
        videoRepository.deleteAll()
    }
    fun getVideoById(videoId : String) :LiveData<VideoEntitiy?>?
    {
        return videoRepository.getVideoById(videoId)
    }
}
class ViewModelFactory(private val repository: VideoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideoPlayerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}