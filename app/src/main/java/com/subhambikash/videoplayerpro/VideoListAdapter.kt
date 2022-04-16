package com.subhambikash.videoplayerpro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.transition.Hold

class VideoListAdapter(private val context: Context, private val dataList:ArrayList<String>): RecyclerView.Adapter<VideoListAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView =itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val listVideos=dataList[position]
        Glide.with(context).load("file://$listVideos").diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.image)

        holder.image.setOnClickListener {
            val intent= Intent(context,VideoPlayer::class.java)
            intent.putExtra("video",listVideos)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}