package com.subhambikash.videoplayerpro

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }else{
            loadVideo()
        }


    }

    private fun loadVideo() {

        val cols= listOf<String>(MediaStore.Video.Media.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME).toTypedArray()
        val cursor=contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,cols,null,null,MediaStore.Video.Media.DATE_ADDED+" DESC")





    }




    private val resultLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            loadVideo()
        }
    }

}