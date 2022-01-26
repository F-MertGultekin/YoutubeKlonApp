package com.example.youtubeklonapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.youtubeklonapp.APIWorker
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.model.Videos
import com.example.youtubeklonapp.service.YoutubeApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class VideoListViewModel : ViewModel(){


    private val youtubeApiService = YoutubeApiService()//youtubeApiService : YoutubeApiService() farkını sor getter setter istiyorbböyle
    private val disposable = CompositeDisposable()

    val videos = MutableLiveData<Videos>()


     fun getDataFromAPI(query: String?,pageToken: String?)
    {
        disposable.add(
            youtubeApiService.getDataService(query,pageToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<Videos>(){
                    override fun onSuccess(t: Videos) {
                        videos.value = t
                    }

                    override fun onError(e: Throwable) {
                        Log.e("error","error")
                    }


                })
        )


    }

}