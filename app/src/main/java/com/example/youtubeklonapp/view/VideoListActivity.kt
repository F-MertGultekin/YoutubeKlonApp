package com.example.youtubeklonapp.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.youtubeklonapp.APIWorker
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

//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 token
//Pagination için https://stackoverflow.com/questions/51433106/kotlin-recyclerview-pagination
//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
// ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs

class VideoListActivity : AppCompatActivity() {
    //private lateinit var videoListViewModel : VideoListViewModel
    private lateinit var searchView: SearchView
    private lateinit var adapter : VideosCardAdapter
    private var isLoading: Boolean = false
    val videoListViewModel: VideoListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_all_videos)
        searchView = findViewById(R.id.svSearch)

        //videoListViewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)

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

        videoListViewModel.videos.observe(this, Observer {
            it?.let {
                adapter = VideosCardAdapter(it.items,this)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter
            }
        })
        /*videoListViewModel.videos.observe(this, {data ->
            data?.let {
                adapter = VideosCardAdapter(data.items,this)
                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter

            }
        })*/

        val mLayoutManager =  LinearLayoutManager (this,LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
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

                }
            })
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(false)
            .build()
        val request = OneTimeWorkRequestBuilder<APIWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(request)
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(this,  {

                val status: String = it.state.name
                Toast.makeText(this,status, Toast.LENGTH_SHORT).show()
            })

    }
}



