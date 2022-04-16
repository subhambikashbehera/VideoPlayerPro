package com.subhambikash.videoplayerpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_all_video_list.*

class AllVideoList : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_video_list)

        try {
            val listVideo=intent.getStringArrayListExtra("listVideo")
            videoListRecycler.layoutManager=GridLayoutManager(this,3)
            val adapter= listVideo?.let { VideoListAdapter(this, it) }
            videoListRecycler.adapter=adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }



}