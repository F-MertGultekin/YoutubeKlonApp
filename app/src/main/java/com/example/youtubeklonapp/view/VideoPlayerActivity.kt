package com.example.youtubeklonapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.example.youtubeklonapp.BuildConfig
import com.example.youtubeklonapp.R
import com.example.youtubeklonapp.VideoDataStoreObject
import com.example.youtubeklonapp.YoutubeKlonApplication
import com.example.youtubeklonapp.entitiy.VideoEntity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class VideoPlayerActivity : AppCompatActivity() {

    private var str :String =""
    private val repository by lazy { YoutubeKlonApplication.repository }
    private val YOUTUBE_API_KEY: String = BuildConfig.API_KEY
    private lateinit var youtubePlayerInit: YouTubePlayer.OnInitializedListener
    private lateinit var youTubePlayer: YouTubePlayerView
    private lateinit var btnPlayer: Button
    private lateinit var btnFavorite : ImageButton
    private lateinit var textView: TextView


    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        const val VIDEO_ID: String = "VIDEO_ID"
        const val VIDEO_TITLE: String= "VIDEO_TITLE"
        private const val VIDEO_DESCRIPTION: String= "VIDEO_DESCRIPTION"
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
        textView = findViewById(R.id.textView)
        btnPlayer.text = intent.getStringExtra(VIDEO_TITLE)
        val videoId = intent.getStringExtra(VIDEO_ID)
        val isExist = repository.exists(videoId)
        if(!isExist){//Entity yoksa
            val videoEntity = VideoEntity(videoId,"false")
            repository.insertVideo(videoEntity)
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
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

        val videoDataStoreObject=VideoDataStoreObject(dataStore)
        btnFavorite.setOnClickListener {
            if(repository.getVideoById(videoId).isFavorite=="false")
            {
                btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                repository.updateVideo(videoId,"true")

                GlobalScope.launch {
                    videoDataStoreObject.saveUserToPreferencesStore(videoId)
                }
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
        for(i in list) {
            if (i.isFavorite == "true") {
                str = str + " ---- " + i.videoId
            }
        }
        btnPlayer.text = str

        videoDataStoreObject.videoIdDataStoreFlow.asLiveData().observe(this, {
            textView.text = it

        })

        initUI()
    }

    private fun initUI() {
        youtubePlayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean)
            {
                val videoId = intent.getStringExtra(VIDEO_ID)
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