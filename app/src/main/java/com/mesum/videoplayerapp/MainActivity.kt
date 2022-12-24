package com.mesum.videoplayerapp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.ThumbnailUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentResolverCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.mesum.videoplayerapp.`interface`.VideoClickInterface
import com.mesum.videoplayerapp.adapter.VideoRvAdapter
import com.mesum.videoplayerapp.model.ViewRvModel

class MainActivity :  AppCompatActivity(), VideoClickInterface {
    private lateinit var rv : RecyclerView
    private var videoRvModelData = mutableListOf<ViewRvModel>()
    private lateinit var videoRvAdapter : VideoRvAdapter
    val STORAGE_PERMISSION = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rvViewView)
        videoRvModelData = mutableListOf()
        videoRvAdapter = VideoRvAdapter(this,this)

        rv.adapter = videoRvAdapter

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION)
        }else{
            getVideo()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                getVideo()
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    @SuppressLint("Range")
    private fun getVideo(){

        var contentResolver = this.contentResolver
        val url = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var cursor = contentResolver.query(url, null, null, null, null )

        if (cursor != null&& cursor.moveToFirst()){

            do {
                var videoTitle = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                var videoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                var videoThumbNail = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)

                val videoData = videoThumbNail?.let { ViewRvModel(videoTitle, videoPath, thumbNail = it) }
                if (videoData != null) {
                    videoRvModelData.add(videoData)
                }
            }while (cursor.moveToNext())
        }
        videoRvAdapter.submitList(videoRvModelData)
      //  videoRvAdapter.notifyDataSetChanged()

    }

    override fun onVideoClick(position: Int) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra("videoName", videoRvModelData.get(position).videoName)
        intent.putExtra("videoPath", videoRvModelData.get(position).videoPath)
        startActivity(intent)
    }
}