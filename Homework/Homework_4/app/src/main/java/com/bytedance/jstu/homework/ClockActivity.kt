package com.bytedance.jstu.homework

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.Clock

class ClockActivity : AppCompatActivity(), View.OnClickListener {

    enum class ClockMode {
        AUTO, MANUAL
    }

    private lateinit var autoClockCanvasView: ClockView
    private lateinit var autoModeButton: View
    private lateinit var manualModeButton: View
    private var mode: ClockMode? = null
    private var clockModeHandler: Handler = Handler(Looper.getMainLooper())

    private fun bindView() {
        autoClockCanvasView = findViewById(R.id.auto_clock)
        autoModeButton = findViewById(R.id.auto_mode_button)
        manualModeButton = findViewById(R.id.manual_mode_button)
    }

    private fun setClickListener() {
        autoModeButton.setOnClickListener(this)
        manualModeButton.setOnClickListener(this)
    }

    var clockAutoModeSwitcher: Runnable = Runnable() {
        run() {
            findViewById<ClockView>(R.id.auto_clock).autoUpdatePinDegree()
            clockModeHandler.postDelayed(clockAutoModeSwitcher, 1000)
        }
    }

    private fun setClockMode(targetMode: ClockMode) {
        if (targetMode != mode) {
            Log.d("ClockActivity", "Change Mode to $targetMode.")
            mode = targetMode
            if (targetMode == ClockMode.AUTO) {
                findViewById<ClockView>(R.id.auto_clock).setMode(0)
                clockModeHandler.post(clockAutoModeSwitcher)
            }
            else {
                findViewById<ClockView>(R.id.auto_clock).setMode(1)
                clockModeHandler.removeCallbacks(clockAutoModeSwitcher)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)
        bindView()
        setClickListener()
        setClockMode(ClockMode.AUTO)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            autoModeButton-> {
                setClockMode(ClockMode.AUTO)
                Toast.makeText(this, "Auto Mode", Toast.LENGTH_SHORT).show()
            }
            manualModeButton -> {
                setClockMode(ClockMode.MANUAL)
                Toast.makeText(this , "Manual Mode", Toast.LENGTH_SHORT).show()
            }
        }
    }
}