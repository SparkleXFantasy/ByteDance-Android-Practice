package com.bytedance.jstu.homework

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), OnClickListener {
    companion object{
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    private lateinit var btnCustomCamera : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        setClickListener()
   }

    private fun bindView() {
        btnCustomCamera = findViewById(R.id.btn_custom_camera)
    }

    private fun setClickListener() {
        btnCustomCamera.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnCustomCamera -> {
                customCamera()
            }
        }
    }

    private fun customCamera() {
        requestPermission()
    }

    private fun recordVideo() {
        CustomCameraActivity.startUI(this)
    }

    private fun requestPermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission && hasAudioPermission) {
            recordVideo()
        } else {
            val permission: MutableList<String> = ArrayList()
            if (!hasCameraPermission) {
                permission.add(Manifest.permission.CAMERA)
            }
            if (!hasAudioPermission) {
                permission.add(Manifest.permission.RECORD_AUDIO)
            }
            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasPermission = true
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false
                break
            }
        }
        if (hasPermission) {
            recordVideo()
        } else {
            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
        }
    }
}