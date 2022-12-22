package com.mesum.videoplayerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mesum.videoplayerapp.`interface`.VideoClickInterface
import com.mesum.videoplayerapp.adapter.VideoRvAdapter
import com.mesum.videoplayerapp.model.ViewRvModel

class MainActivity : AppCompatActivity(), VideoClickInterface {
    private lateinit var rv : RecyclerView
    private var videoRvModelData = mutableListOf<ViewRvModel>()
    private lateinit var videoRvAdapter : VideoRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rvViewView)
        videoRvModelData = mutableListOf()
        videoRvAdapter = VideoRvAdapter(this,this)


    }

    override fun onVideoClick(position: Int) {
        TODO("Not yet implemented")
    }
}