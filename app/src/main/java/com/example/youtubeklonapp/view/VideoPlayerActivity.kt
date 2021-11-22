package com.example.youtubeklonapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class VideoPlayerActivity : YouTubeBaseActivity() {

    lateinit var youTubePlayer: YouTubePlayerView
    lateinit var btnPlayer: Button
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
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
        //val creatureById = CreatureStore.getCreatureById(intent.getIntExtra(EXTRA_CREATURE_ID, 1))

        btnPlayer.setText(intent.getStringExtra(VIDEO_TITLE))
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