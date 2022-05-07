package com.bytedance.jstu.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var btnImage : Button? = null
    private var btnVideo : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindBtn()
        setClickListener()
    }

    private fun bindBtn() {
        btnImage = findViewById(R.id.image)
        btnVideo = findViewById(R.id.video)
    }

    private fun setClickListener() {
        btnImage?.setOnClickListener(this)
        btnVideo?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0) {
            btnImage -> {
                val intent = Intent(this, ImageActivity::class.java)
                startActivity(intent)
            }
            btnVideo -> {
                val intent = Intent(this, VideoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}