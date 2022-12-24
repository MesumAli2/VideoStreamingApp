package com.mesum.videoplayerapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.ContactsContract.CommonDataKinds.Relation
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import org.w3c.dom.Text


class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var videoNameTv : TextView
    private lateinit var videoTime : TextView
    private lateinit var backBtn : ImageButton
    private lateinit var forwardBtn : ImageButton
    private lateinit var playPauseBtn : ImageButton
    private lateinit var videoSeekBar : SeekBar
    private lateinit var videoName : String
    private lateinit var videoView  : VideoView
    private lateinit var controlRl : RelativeLayout
    private lateinit var videoRl : RelativeLayout
    private var isOpen = true
    private lateinit var videoPath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        videoName = intent.getStringExtra("videoName").toString()
        videoPath = intent.getStringExtra("videoPath").toString()
        videoNameTv = findViewById(R.id.idTvVideoTitle)
        videoTime = findViewById(R.id.idTvTime)
        backBtn = findViewById(R.id.idBack)
        playPauseBtn = findViewById(R.id.idPlay)
        forwardBtn = findViewById(R.id.idForward)
        videoSeekBar = findViewById(R.id.seekBarprogress)
        videoView = findViewById(R.id.IdVideoView)
        controlRl = findViewById(R.id.idRlControls)
        videoRl = findViewById(R.id.idRl_video)

        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.setOnPreparedListener(object  : MediaPlayer.OnPreparedListener{
            override fun onPrepared(p0: MediaPlayer?) {
                videoSeekBar.max = videoView.duration
                videoView.start()
            }

        })

        videoNameTv.text = videoName
        backBtn.setOnClickListener {
            videoView.seekTo(videoView.duration - 10000)

        }
        forwardBtn.setOnClickListener {
            videoView.seekTo(videoView.duration + 10000)
        }
        playPauseBtn.setOnClickListener {
            if (videoView.isPlaying){
                videoView.pause()
                playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_play_circle_24))

            }else{
                videoView.start()
                playPauseBtn.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_pause_circle_outline_24))
            }
        }

        videoRl.setOnClickListener {
            if (isOpen){
                hideControls()
                isOpen = false
            }else{
                showControls()
                isOpen = true
            }
        }
    }

    private fun showControls() {
    controlRl.visibility = View.VISIBLE
        val window = this.window
        if ( window == null){
            return
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        val decorView = window.decorView
        if (decorView != null){
            var uiOption = decorView.systemUiVisibility
            if (Build.VERSION.SDK_INT >= 14){
                uiOption = View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
            if (Build.VERSION.SDK_INT >= 19){
                uiOption = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            decorView.systemUiVisibility = uiOption
        }
    }

    private fun hideControls() {
        controlRl.visibility = View.GONE
        val window = this.window
        if ( window == null){
            return
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        val decorView = window.decorView
        if (decorView != null){
            var uiOption = decorView.systemUiVisibility
            if (Build.VERSION.SDK_INT >= 14){
                uiOption = View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
            if (Build.VERSION.SDK_INT >= 19){
                uiOption = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            decorView.systemUiVisibility = uiOption
        }    }
}