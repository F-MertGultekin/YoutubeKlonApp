package com.example.youtubeklonapp.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.databinding.FragmentAllVideosBinding
import com.example.youtubeklonapp.model.Item
import com.example.youtubeklonapp.viewmodel.VideoListViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


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
    private fun setListeners() {
        lifecycleScope.launchWhenStarted {
            videoListViewModel.videos.collect() {
                val tempList: MutableList<Item> = mutableListOf()
                it?.let {
                    nextPageToken = it.nextPageToken
                    it.items.forEach { it ->
                        tempList.add(it)
                    }
                    tempList.forEach {
                        videoItemList.add(it)
                    }
                    binding.videoRecyclerView.apply {
                        tempList.clear()
                        adapter = VideosCardAdapter(
                            ArrayList(videoItemList),
                            context = this@VideoListActivity,
                            initView
                        )
                        (adapter as VideosCardAdapter).notifyDataSetChanged()

                    }
                }
            }
        }
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
    private fun initKoin() {

        //startKoin(this, listOf(ServiceDependency,VideoListRepositoryDependency,ViewModelDependency))


        //https://insert-koin.io/docs/quickstart/kotlin
        //https://github.com/InsertKoinIO/koin/blob/main/quickstart/getting-started-koin-core/src/main/kotlin/org/koin/sample/HelloApplication.kt
        //https://github.com/sreeharikv112/KoinDIExample/blob/master/app/src/main/java/com/dev/koindiexample/mainapp/MainApplication.kt
        //https://medium.com/@sreeharikv112/dependency-injection-using-koin-4de4a3494cbe
        //https://medium.com/swlh/dependency-injection-with-koin-6b6364dc8dba
        //https://medium.com/gradeup/dependency-injection-dagger-hilt-vs-koin-ab2f7f85e6c6
    }


}



