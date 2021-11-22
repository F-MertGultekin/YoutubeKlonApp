package com.example.youtubeklonapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtubeklonapp.R
import android.content.Intent




class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashScreenActivity, VideoListActivity::class.java))
        finish()
    }
}