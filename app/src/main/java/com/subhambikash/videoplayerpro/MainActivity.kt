package com.subhambikash.videoplayerpro

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var videoFolder= arrayListOf<VideoFolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                loadVideo()
            }

        }


    }

    private fun loadVideo() {
        var isFolder=false
        var position=0

        val cols= listOf<String>(MediaStore.Video.Media.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME).toTypedArray()
        val cursor=contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,cols,null,null,MediaStore.Video.Media.DATE_ADDED+" DESC")
        val folderNameIndex=cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        val videoPathIndex=cursor?.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

        if (cursor!=null){
            while (cursor.moveToNext()){
                val folderName=cursor.getString(folderNameIndex!!)
                val videoPath=cursor.getString(videoPathIndex!!)

                for (i in 0 until videoFolder.size){
                    if (videoFolder[i].folderName==folderName){
                        isFolder=true
                        position=i
                        break
                    }else{
                        isFolder=false
                    }
                }

              if (isFolder){
                  var arrayListOf=ArrayList<String>()
                  arrayListOf=videoFolder[position].videoList
                  arrayListOf.add(videoPath)
                  videoFolder[position].videoList=arrayListOf
              }else{
                  val videoList=ArrayList<String>()
                  videoList.add(videoPath)
                  videoFolder.add(VideoFolder(folderName,videoList))
              }
            }
        }

        MainScope().launch {
            val layoutManager=GridLayoutManager(this@MainActivity,3)
            recyclerView.layoutManager=layoutManager
            val adapter=FolderAdapter()
            adapter.differ.submitList(videoFolder)
            recyclerView.adapter=adapter
            adapter.notifyDataSetChanged()
        }

    }




    private val resultLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            CoroutineScope(Dispatchers.IO).launch {
                loadVideo()
            }
        }
    }

}