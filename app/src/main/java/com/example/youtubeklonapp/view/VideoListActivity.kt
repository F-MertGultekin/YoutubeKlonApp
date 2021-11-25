package com.example.youtubeklonapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.viewmodel.VideoListViewModel

//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
// ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs
class VideoListActivity : AppCompatActivity() {
    private lateinit var videoListViewModel : VideoListViewModel
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_all_videos)
        searchView = findViewById(R.id.svSearch)

        videoListViewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                videoListViewModel.getDataFromAPI("snippet","date","5","video", BuildConfig.API_KEY,query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                videoListViewModel.getDataFromAPI("snippet","date","5","video", BuildConfig.API_KEY,newText)
                return true
            }

        })

        videoListViewModel.getDataFromAPI("snippet","date","5","video", BuildConfig.API_KEY,"")
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



