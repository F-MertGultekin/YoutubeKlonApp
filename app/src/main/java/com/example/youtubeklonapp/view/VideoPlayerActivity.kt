package com.example.youtubeklonapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.viewmodel.VideoPlayerViewModel
import com.example.youtubeklonapp.viewmodel.ViewModelFactory
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeklonapp.YoutubeKlonApplication
import com.example.youtubeklonapp.database.VideoDatabase
import com.example.youtubeklonapp.repository.VideoRepository
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeklonapp.entitiy.VideoEntitiy


class VideoPlayerActivity : AppCompatActivity() {// YouTubeBaseActivity()

    lateinit var youTubePlayer: YouTubePlayerView
    lateinit var btnPlayer: Button
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
    lateinit var btnFavorite : ImageButton
    //lateinit var videoPlayerViewModel : VideoPlayerViewModel
    //var database: VideoDatabase = VideoDatabase.getDatabase(this)
    //var repository: VideoRepository = VideoRepository(database.videoDao())
    //var factory: ViewModelFactory = ViewModelFactory(repository)
    var uniqueCount : Int = 0

    private val viewModel: VideoPlayerViewModel by viewModels {
        ViewModelFactory((application as YoutubeKlonApplication).repository)
    }

    val YOUTUBE_API_KEY: String = BuildConfig.API_KEY
    companion object {
        const val VIDEO_ID: String = "VIDEO_ID"
        const val VIDEO_TITLE: String= "VIDEO_TITLE"
        const val VIDEO_DESCRIPTION: String= "VIDEO_DESCRIPTION"
        fun newIntent(context: Context, itemId: String, title : String, description: String): Intent {
            val intent = Intent(context, VideoPlayerActivity::class.java)
            intent.putExtra(VIDEO_ID, itemId)
            intent.putExtra(VIDEO_TITLE,title)
            intent.putExtra(VIDEO_DESCRIPTION,description)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        youTubePlayer= findViewById(R.id.youtubePlayer)
        btnPlayer= findViewById(R.id.btnPlay)
        btnFavorite = findViewById(R.id.favoriteButton)

        //videoPlayerViewModel = ViewModelProvider(this,factory)[VideoPlayerViewModel::class.java]
        initUI()
        btnFavorite.setOnClickListener {
            val LiveDataSelectedVideo = viewModel.getVideoById(VIDEO_ID)
            if (LiveDataSelectedVideo != null) {
                LiveDataSelectedVideo.observe(this, {data ->
                    data?.let {
                        viewModel.delete(data)
                        btnFavorite.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    }
                })
            }
            else{
                val videoEntitiy = VideoEntitiy(uniqueCount++, VIDEO_ID,"true")//uniquecount yerine shared peferences kullanılabilir
                viewModel.insert(videoEntitiy)
                btnFavorite.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
            }



        }
    }
    private fun initUI() {
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean)
            {
                var videoId = intent.getStringExtra(VIDEO_ID)
                youtubePlayer?.loadVideo(videoId)
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Toast.makeText(applicationContext, "Something went wrong !! ", Toast.LENGTH_SHORT).show()
            }
        }
        btnPlayer.setOnClickListener {
            youTubePlayer.initialize(YOUTUBE_API_KEY,youtubePlayerInit)
        }

    }
    fun findVideo(videoEntities : List<VideoEntitiy>) : VideoEntitiy?
    {

        for (video in videoEntities)
        {
            if(video.videoId== VIDEO_ID){
                return video
            }
        }
        return null
    }
}