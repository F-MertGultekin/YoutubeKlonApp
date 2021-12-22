package com.example.youtubeklonapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.model.Videos
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
//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
//Pagination için https://stackoverflow.com/questions/51433106/kotlin-recyclerview-pagination
//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
// ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs
class VideoListActivity : AppCompatActivity() {
    private lateinit var videoListViewModel : VideoListViewModel
    lateinit var searchView: SearchView
    lateinit var adapter : VideosCardAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

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
        val recyclerView = findViewById<RecyclerView>(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

        videoListViewModel.videos.observe(this, {data ->
            data?.let {
                adapter = VideosCardAdapter(data.items,this)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter

            }
        })

        var mLayoutManager =  LinearLayoutManager (this,LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager;
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = mLayoutManager.childCount
                val pastVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoading) {

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        addMoreData()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
    fun addMoreData()
    {

            videoListViewModel.getDataFromAPI("snippet","date","5","video", BuildConfig.API_KEY,"")
            videoListViewModel.videos.observe(this, {data ->
                data?.let {
                    isLoading = false
                    adapter.addData(data.items)

                    //VideoArrayList.add(data)

                    //adapter.notifyItemInserted((VideoArrayList.size)-1)
                    //now adding the adapter to recyclerview
                    //adapter.notifyDataSetChanged()


                }
            })


    }
}



