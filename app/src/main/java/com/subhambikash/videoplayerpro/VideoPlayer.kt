package com.subhambikash.videoplayerpro

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import java.io.File

class VideoPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_video_player)
        supportActionBar?.hide()


        val uri = intent.getStringExtra("video").toString()

        val mediaController = MediaController(this)
        val videoView = findViewById<VideoView>(R.id.videoView)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.fromFile(File(uri)))
        videoView.setOnPreparedListener {
            videoView.start()
        }

    }
}