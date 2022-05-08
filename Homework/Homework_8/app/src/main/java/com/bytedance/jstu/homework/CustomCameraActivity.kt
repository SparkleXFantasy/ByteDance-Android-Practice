package com.bytedance.jstu.homework

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bytedance.jstu.homework.PathUtils.rotateImage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class CustomCameraActivity : AppCompatActivity(), SurfaceHolder.Callback, View.OnClickListener {
    private lateinit var svCustomCamera: SurfaceView
    private lateinit var ivCustomCamera: ImageView
    private lateinit var vvCustomCamera: VideoView
    private lateinit var btnCustomCameraImage : Button
    private lateinit var btnCustomCameraVideo : Button
    private lateinit var svHolder: SurfaceHolder
    private var camera: Camera? = null
    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false
    private var mp4Path = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_camera)
        bindView()
        initResource()
        setClickListener()
    }

    private fun bindView() {
        svCustomCamera = findViewById(R.id.sv_custom_camera)
        ivCustomCamera = findViewById(R.id.iv_custom_camera)
        vvCustomCamera = findViewById(R.id.vv_custom_camera)
        btnCustomCameraImage = findViewById(R.id.btn_custom_camera_img)
        btnCustomCameraVideo = findViewById(R.id.btn_custom_camera_video)
        svHolder = svCustomCamera.holder
        svHolder.addCallback(this)
    }

    private fun setClickListener() {
        btnCustomCameraVideo.setOnClickListener(this)
        btnCustomCameraImage.setOnClickListener(this)
    }

    private fun initResource() {
        if (camera == null) {
            camera = Camera.open()
            camera?.let {
                val parameters = it.parameters
                parameters.pictureFormat = ImageFormat.JPEG
                parameters["orientation"] = "portrait"
                parameters["rotation"] = 90
                it.parameters = parameters
                it.setDisplayOrientation(90)
            }
        }
    }

    private fun prepareVideoRecorder(): Boolean {
        val mediaRecorder = MediaRecorder()
        this.mediaRecorder = mediaRecorder
        camera?.unlock()
        mediaRecorder.setCamera(camera)
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
        mp4Path = outputMediaPath
        mediaRecorder.setOutputFile(mp4Path)
        mediaRecorder.setPreviewDisplay(svHolder.surface)
        mediaRecorder.setOrientationHint(90)
        try {
            mediaRecorder.prepare()
        } catch (e: IllegalStateException) {
            releaseMediaRecorder()
            return false
        } catch (e: IOException) {
            releaseMediaRecorder()
            return false
        }
        return true
    }

    private fun releaseMediaRecorder() {
        mediaRecorder?.let { mediaRecorder->
            mediaRecorder.reset()
            mediaRecorder.release()
            this.mediaRecorder = null
            camera.let {
                it!!.lock()
            }
        }
    }

    private val outputMediaPath: String
        private get() {
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile = File(mediaStorageDir, "IMG_$timeStamp.mp4")
            if (!mediaFile.exists()) {
                mediaFile.parentFile.mkdirs()
            }
            return mediaFile.absolutePath
        }

    fun takePhoto() {
        camera?.takePicture(null, null, pictureCallback)
    }

    var pictureCallback = Camera.PictureCallback { data, camera ->
        var fos: FileOutputStream? = null
        val filePath =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "1.jpg"
        val file = File(filePath)
        try {
            fos = FileOutputStream(file)
            fos.write(data)
            fos.flush()
            val bitmap = BitmapFactory.decodeFile(filePath)
            val rotateBitmap = rotateImage(bitmap, filePath)
            ivCustomCamera.visibility = View.VISIBLE
            vvCustomCamera.visibility = View.GONE
            ivCustomCamera.setImageBitmap(rotateBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            this.camera?.startPreview()
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun record() {
        if (isRecording && mediaRecorder != null) {
            btnCustomCameraVideo.text = "录制"
            mediaRecorder = this.mediaRecorder
            if (mediaRecorder == null) {
                return
            }
            mediaRecorder!!.setOnErrorListener(null)
            mediaRecorder!!.setOnInfoListener(null)
            mediaRecorder!!.setPreviewDisplay(null)
            try {
                mediaRecorder!!.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            vvCustomCamera.visibility = View.VISIBLE
            ivCustomCamera.visibility = View.GONE
            vvCustomCamera.setVideoPath(mp4Path)
            vvCustomCamera.start()
        } else {
            if (prepareVideoRecorder()) {
                btnCustomCameraVideo.text = "暂停"
                mediaRecorder!!.start()
            }
        }
        isRecording = !isRecording
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (holder.surface == null) {
            return
        }
        camera?.stopPreview()
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.let {
            it.stopPreview()
            it.release()
        }
    }

    override fun onResume() {
        super.onResume()
        if (camera == null) {
            initResource()
        }
        camera?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        camera?.stopPreview()
    }

    companion object {
        fun startUI(context: Context) {
            val intent = Intent(context, CustomCameraActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnCustomCameraImage -> {
                takePhoto()
            }
            btnCustomCameraVideo -> {
                record()
            }
        }
    }
}