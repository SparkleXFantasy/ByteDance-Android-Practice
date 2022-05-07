package com.bytedance.jstu.homework

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.MediaController

class VideoActivity : AppCompatActivity(), View.OnClickListener {
    private var videoView : VideoLayout? = null
    private var btnPlayView : Button? = null
    private var btnPauseView : Button? = null
    private var btnResumeView : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        Log.d("Activity", "VideoActivity")
        bindView()
        setClickListener()
        setResource()
    }

    private fun bindView() {
        videoView = findViewById(R.id.video_view)
        btnPlayView = findViewById(R.id.btn_start)
        btnPauseView = findViewById(R.id.btn_pause)
        btnResumeView = findViewById(R.id.btn_resume)
    }

    private fun setClickListener() {
        btnPlayView?.setOnClickListener(this)
        btnPauseView?.setOnClickListener(this)
        btnResumeView?.setOnClickListener(this)
    }

    private fun setResource() {
        videoView?.setVideoPath(getVideoPath(R.raw.film))
        videoView?.setMediaController(MediaController(this))
        Log.d("VideoActivity", "Finish setting resources")
    }

    private fun getVideoPath(resId: Int): String {
        return "android.resource://" + this.packageName + "/" + resId
    }

    @Override
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d( "VideoActivity", "Orientation")
        setContentView(R.layout.activity_video)
        bindView()
        setClickListener()
        setResource()
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnPlayView -> {
                videoView?.start()
            }
            btnPauseView -> {
                videoView?.pause()
            }
            btnResumeView -> {
                videoView?.resume()
            }
        }
    }
}