package com.example.youtubeklonapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.YoutubeKlonApplication
import com.example.youtubeklonapp.entitiy.VideoEntity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class VideoPlayerActivity : AppCompatActivity() {

    var str :String =""
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
    lateinit var youTubePlayer: YouTubePlayerView
    val YOUTUBE_API_KEY: String = BuildConfig.API_KEY
    lateinit var btnPlayer: Button

    lateinit var btnFavorite : ImageButton
    private val repository by lazy { YoutubeKlonApplication.repository }

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
        btnFavorite = findViewById(R.id.btnFavorite)
        btnPlayer.text = intent.getStringExtra(VIDEO_TITLE)
        var videoId = intent.getStringExtra(VIDEO_ID)
        val isExist = repository.exists(videoId)
        if(!isExist){//Entity yoksa
            val videoEntity = VideoEntity(videoId,"false")
            repository.insertVideo(videoEntity)

        }
        else{//Entity varsa
            if(repository.getVideoById(videoId).isFavorite=="false")//ve favori değilse
            {
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            else
            {
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24

                )
            }
        }
        btnFavorite.setOnClickListener {
            if(repository.getVideoById(videoId).isFavorite=="false")
            {
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                repository.updateVideo(videoId,"true")
                //resim ayarla
                //update et
            }
            else//favori ise
            {
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                repository.updateVideo(videoId,"false")
                //sileceksin
                //resim değişecek
            }

        }

        val list = repository.getVideos()
        for(i in list){
            str =str+" ---- " +i.videoId
        }
        btnPlayer.text = str


        initUI()
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
}