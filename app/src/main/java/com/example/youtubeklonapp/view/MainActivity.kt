package com.example.youtubeklonapp.view

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.youtubeklonapp.R
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

//ceng hesabı api AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0
// gmail hesabı api AIzaSyBL6zWafLr1KCpazirfcVMu3ufCFMKkbfs
class MainActivity : YouTubeBaseActivity() {

    lateinit var youTubePlayer: YouTubePlayerView
    lateinit var btnPlayer: Button
    lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener

    companion object {
        const val VIDEO_ID: String = "YqggV_wGhM0";
        const val YOUTUBE_API_KEY: String = "AIzaSyBRCMfcJeQmzwztn_OP_C5kfW09fi3pqx0"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        youTubePlayer= findViewById(R.id.youtubePlayer)
        btnPlayer= findViewById(R.id.btnPlay)
        initUI()
    }

    private fun initUI() {
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean)
            {
                youtubePlayer?.loadVideo(VIDEO_ID)
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