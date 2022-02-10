package com.example.youtubeklonapp.viewmodel
//https://www.lukaslechner.com/comparing-kotlin-coroutines-with-callbacks-and-rxjava/
//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050
//https://medium.com/mobile-app-development-publication/injecting-viewmodel-from-hard-to-easy-c06c0fe1c8e9
//https://medium.com/android-beginners/mvvm-with-kotlin-coroutines-and-retrofit-example-d3f5f3b09050

import androidx.lifecycle.ViewModel
import com.example.youtubeklonapp.model.Videos
import com.example.youtubeklonapp.repository.NetworkRepository
import com.example.youtubeklonapp.service.YoutubeApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class VideoListViewModel(val networkRepository : NetworkRepository) : ViewModel() {
    val videos = MutableSharedFlow<Videos>()

    fun getDataFromAPI(query: String?, pageToken: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = networkRepository.getDataRepository(query, pageToken)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { videos.emit(it) }

                }
            }
        }
    }

}