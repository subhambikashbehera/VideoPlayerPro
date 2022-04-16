package com.subhambikash.videoplayerpro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import java.io.File


class FolderAdapter(): RecyclerView.Adapter<FolderAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<VideoFolder>() {
        override fun areItemsTheSame(oldItem: VideoFolder, newItem: VideoFolder): Boolean {
            return oldItem.folderName == newItem.folderName
        }

        override fun areContentsTheSame(oldItem: VideoFolder, newItem: VideoFolder): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.folderImage)
        val folderName: TextView = itemView.findViewById(R.id.folderName)
        val count: TextView = itemView.findViewById(R.id.count)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val videoFolder= differ.currentList[position]

        holder.folderName.text = videoFolder.folderName
        holder.count.text = videoFolder.videoList.size.toString()

        val options = RequestOptions()
            .centerCrop()
            .placeholder(android.R.drawable.stat_sys_download)
            .error(android.R.drawable.stat_notify_error)

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(videoFolder.videoList[0])
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(options)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent=Intent(holder.itemView.context,AllVideoList::class.java)
            intent.putStringArrayListExtra("listVideo",videoFolder.videoList)
            holder.itemView.context.startActivity(intent)
        }



    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}