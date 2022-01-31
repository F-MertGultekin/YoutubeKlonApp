package com.example.youtubeklonapp.viewmodel
//https://www.lukaslechner.com/comparing-kotlin-coroutines-with-callbacks-and-rxjava/
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.services.youtube.YouTube
import io.reactivex.Single
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import android.util.Log
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.youtubeklonapp.APIWorker
import com.example.youtubeklonapp.model.Videos
import com.example.youtubeklonapp.service.YoutubeApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


class VideoListViewModel : ViewModel() {
    private val youtubeApiService = YoutubeApiService()//youtubeApiService : YoutubeApiService() farkını sor getter setter istiyorbböyle
    val videos = MutableSharedFlow<Videos>()

    fun getDataFromAPI(query: String?, pageToken: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = youtubeApiService.getDataService(query, pageToken)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { videos.emit(it) }

                }
            }
        }
    }

}