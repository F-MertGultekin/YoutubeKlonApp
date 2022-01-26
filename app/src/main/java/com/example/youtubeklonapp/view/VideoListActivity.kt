package com.example.youtubeklonapp.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.databinding.FragmentAllVideosBinding
import com.example.youtubeklonapp.model.Item
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

//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 token
//Pagination için https://stackoverflow.com/questions/51433106/kotlin-recyclerview-pagination
//https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&key=AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0&order=date&type=video
// ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs

class VideoListActivity : AppCompatActivity() {
    lateinit var binding: FragmentAllVideosBinding
    private val videoListViewModel: VideoListViewModel by viewModel()

    val videoItemList: MutableList<Item> = mutableListOf()
    private var defaultQuery: String? = ""
    private var nextPageToken: String = "CAoQAA"
    private val firstPageToken: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.fragment_all_videos)
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                videoItemList.clear()
                defaultQuery = query
                addMoreData(videoItemList, firstPageToken)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                videoItemList.clear()
                defaultQuery = newText
                addMoreData(videoItemList, firstPageToken)
                return true
            }

        })
        setListeners()
        addMoreData(videoItemList, firstPageToken)
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.videoRecyclerView.layoutManager = mLayoutManager
        binding.videoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = binding.videoRecyclerView.childCount
                val pastVisibleItem = mLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = binding.videoRecyclerView.adapter?.itemCount

                    if ((visibleItemCount + pastVisibleItem) >= total!!) {
                        addMoreData(videoItemList, nextPageToken)
                    }

            }
        })
    }

    private val initView = View.OnClickListener {
        val item = it.tag as Item
        val intent = VideoPlayerActivity.newIntent(
            this,
            item.id.videoId,
            item.snippet.title,
            item.snippet.description
        )
        this.startActivity(intent)
    }
    private fun setListeners(){
        videoListViewModel.videos.observe(this, { data ->
            val tempList: MutableList<Item> = mutableListOf()
            data?.let {
                nextPageToken=data.nextPageToken
                data.items.forEach { it ->
                    tempList.add(it)
                }
                tempList.forEach{
                    videoItemList.add(it)
                }
                binding.videoRecyclerView.apply {
                    tempList.clear()
                    adapter =VideosCardAdapter(ArrayList(videoItemList), context = this@VideoListActivity, initView)
                    (adapter as VideosCardAdapter).notifyDataSetChanged()

                }

            }
        })
    }
    fun addMoreData(videoItemList: MutableList<Item>, pageToken: String) {

        videoListViewModel.getDataFromAPI(defaultQuery, pageToken)


        /*val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(false)
            .build()
        val request = OneTimeWorkRequestBuilder<APIWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(request)
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id)
            .observe(this, {

                val status: String = it.state.name
                Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
            })*/

    }
}



