package com.example.youtubeklonapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.viewmodel.VideoListViewModel

//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
// ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs
class VideoListActivity : AppCompatActivity() {
    private lateinit var videoListViewModel : VideoListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_all_videos)
        videoListViewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        videoListViewModel.getDataFromAPI("snippet","date","5","date", BuildConfig.API_KEY)
        getLiveData()
    }
    private fun getLiveData() {
        videoListViewModel.videos.observe(this, {data ->
            data?.let {
                val recyclerView = findViewById<RecyclerView>(R.id.videoRecyclerView)
                recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
                val adapter = VideosCardAdapter(data,this)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter
            }
        })
    }
}



