package com.bytedance.jstu.homework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var hourPinDegree: Double = 90.0
    private var minutePinDegree: Double = 270.0
    private var secondPinDegree: Double = 270.0
    private val centerX: Float = 500f
    private val centerY: Float = 500f
    private val radius: Float = 500f

    private var touchX: Float? = 0f
    private var touchY: Float? = 0f
    private var mode: Int? = null

    private var hourPinAttr: Paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        strokeWidth = 15f
    }

    private var minutePinAttr: Paint = Paint().apply {
        color = Color.CYAN
        style = Paint.Style.FILL
        strokeWidth = 12f
    }

    private var secondPinAttr: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 10f
    }

    private var minorScaleAttr: Paint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        strokeWidth = 6f
    }

    private var majorScaleAttr: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 8f
        textSize = 100f
        textAlign = Paint.Align.CENTER
    }

    private var textAttr: Paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 8f
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawClock(canvas)
    }

    private fun drawClock(canvas: Canvas?) {
        drawClockScale(canvas)
        drawClockPin(canvas)
        drawClockText(canvas)
    }

    private fun drawClockScale(canvas: Canvas?) {
        for (deg in 0..354 step 6) {
            val x1: Float = (0.9 * radius * cos(deg.toDouble() * Math.PI / 180)).toFloat() + centerX
            val y1: Float = (0.9 * radius * sin(deg.toDouble() * Math.PI / 180)).toFloat() + centerY
            val x2: Float = (radius * cos(deg.toDouble() * Math.PI / 180)).toFloat() + centerX
            val y2: Float = (radius * sin(deg.toDouble() * Math.PI / 180)).toFloat() + centerY
            if (deg % 30 == 0) {
                canvas?.drawLine(x1, y1, x2, y2, majorScaleAttr)
            }
            else {
                canvas?.drawLine(x1, y1, x2, y2, minorScaleAttr)
            }
        }

        var x = (0.8 * radius * cos( 5.5 * Math.PI / 180)).toFloat() + centerX
        var y = (0.8 * radius * sin( 5.5 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("3", x, y, majorScaleAttr)
        x = (0.82 * radius * cos( 34 * Math.PI / 180)).toFloat() + centerX
        y = (0.82 * radius * sin( 34 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("4", x, y, majorScaleAttr)
        x = (0.84 * radius * cos( 62 * Math.PI / 180)).toFloat() + centerX
        y = (0.84 * radius * sin( 62 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("5", x, y, majorScaleAttr)
        x = (0.85 * radius * cos( 90 * Math.PI / 180)).toFloat() + centerX
        y = (0.85 * radius * sin( 90 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("6", x, y, majorScaleAttr)
        x = (0.84 * radius * cos( 118 * Math.PI / 180)).toFloat() + centerX
        y = (0.84 * radius * sin( 118 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("7", x, y, majorScaleAttr)
        x = (0.82 * radius * cos( 145 * Math.PI / 180)).toFloat() + centerX
        y = (0.82 * radius * sin( 145 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("8", x, y, majorScaleAttr)
        x = (0.8 * radius * cos( 174 * Math.PI / 180)).toFloat() + centerX
        y = (0.8 * radius * sin( 174 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("9", x, y, majorScaleAttr)
        x = (0.71 * radius * cos( 205 * Math.PI / 180)).toFloat() + centerX
        y = (0.71 * radius * sin( 205 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("10", x, y, majorScaleAttr)
        x = (0.69 * radius * cos( 239 * Math.PI / 180)).toFloat() + centerX
        y = (0.69 * radius * sin( 239 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("11", x, y, majorScaleAttr)
        x = (0.69 * radius * cos( 270 * Math.PI / 180)).toFloat() + centerX
        y = (0.69 * radius * sin( 270 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("12", x, y, majorScaleAttr)
        x = (0.72 * radius * cos( 304 * Math.PI / 180)).toFloat() + centerX
        y = (0.72 * radius * sin( 304 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("1", x, y, majorScaleAttr)
        x = (0.76 * radius * cos( 335 * Math.PI / 180)).toFloat() + centerX
        y = (0.76 * radius * sin( 335 * Math.PI / 180)).toFloat() + centerY
        canvas?.drawText("2", x, y, majorScaleAttr)
    }

    private fun drawClockPin(canvas: Canvas?) {
        var x: Float = ((0.3 * radius * cos(hourPinDegree * Math.PI / 180)) + centerX).toFloat()
        var y: Float = ((0.3 * radius  * sin(hourPinDegree * Math.PI / 180)) + centerY).toFloat()
        canvas?.drawLine(centerX, centerY, x, y, hourPinAttr)
        canvas?.drawCircle(x, y, 5f, hourPinAttr)

        x = ((0.5 * radius * cos(minutePinDegree * Math.PI / 180)) + centerX).toFloat()
        y = ((0.5 * radius * sin(minutePinDegree * Math.PI / 180)) + centerY).toFloat()
        canvas?.drawLine(centerX, centerY, x, y, minutePinAttr)
        canvas?.drawCircle(x, y, 5f, minutePinAttr)

        x = ((0.7 * radius * cos(secondPinDegree * Math.PI / 180)) + centerX).toFloat()
        y = ((0.7 * radius * sin(secondPinDegree * Math.PI / 180)) + centerY).toFloat()
        canvas?.drawLine(centerX, centerY, x, y, secondPinAttr)
        canvas?.drawCircle(x, y, 5f, secondPinAttr)
        canvas?.drawCircle(centerX, centerY, 5f, secondPinAttr)
    }

    private fun drawClockText(canvas: Canvas?) {
        var hour = (hourPinDegree / 30 + 3).toInt()
        val min: Int = (minutePinDegree / 6 + 15).toInt() % 60
        val sec: Int = (secondPinDegree / 6 + 15).toInt() % 60
        if (hour > 12) {
            hour -= 12
        }
        var hourStr = hour.toString()
        if (hour < 10)
            hourStr = "0" + hourStr
        var minuteStr = min.toString()
        if (min < 10)  minuteStr = "0" + minuteStr
        var secondStr = sec.toString()
        if (sec < 10)  secondStr = "0" + secondStr
        canvas?.drawText("$hourStr : $minuteStr : $secondStr", 500f, 800f, textAttr)
    }

    fun autoUpdatePinDegree() {
        hourPinDegree += 1.0 / 120
        minutePinDegree += 0.1
        secondPinDegree += 6.0
        if (hourPinDegree >= 360) {
            hourPinDegree -= 360
        }
        if (minutePinDegree >= 360) {
            minutePinDegree -= 360
        }
        if (secondPinDegree >= 360) {
            secondPinDegree -= 360
        }
        this.invalidate()
    }

    fun setMode(targetMode: Int) {
        mode = targetMode
    }

    private fun manualUpdatePinDegree(PinId: Int?, PinDegree: Double) {
        when (PinId) {
            0 -> {    // hour pin
                if (hourPinDegree - PinDegree >= 354)  {
                    minutePinDegree += (PinDegree + 360 - hourPinDegree) * 12
                } else if (PinDegree - hourPinDegree >= 354) {
                    minutePinDegree += (PinDegree - 360 - hourPinDegree) * 12
                } else {
                    minutePinDegree += (PinDegree - hourPinDegree) * 12
                }
                hourPinDegree = PinDegree
            }
            1 -> {    // minute pin
                if (minutePinDegree - PinDegree >= 354)  {
                    hourPinDegree += (PinDegree + 360 - minutePinDegree) / 12
                } else if (PinDegree - minutePinDegree >= 354) {
                    hourPinDegree += (PinDegree - 360 - minutePinDegree) / 12
                } else {
                    hourPinDegree += (PinDegree - minutePinDegree) / 12
                }
                minutePinDegree = PinDegree
            }
            2 -> {    // second pin
                if (secondPinDegree - PinDegree >= 354)  {
                    minutePinDegree += (PinDegree + 360 - secondPinDegree) / 60
                    hourPinDegree += (PinDegree + 360 - secondPinDegree) / 720
                } else if (PinDegree - secondPinDegree >= 354) {
                    minutePinDegree += (PinDegree - 360 - secondPinDegree) / 60
                    hourPinDegree += (PinDegree - 360 - secondPinDegree) / 720
                } else {
                    minutePinDegree += (PinDegree - secondPinDegree) / 60
                    hourPinDegree += (PinDegree - secondPinDegree) / 720
                }
                secondPinDegree = PinDegree
            }
        }
        this.invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        if (mode == 0) {    // Auto Mode
            return true
        }
        this.touchX = event?.getX()
        this.touchY = event?.getY()

        var pinDegree: Double = Math.toDegrees(Math.atan(((touchY as Float - centerY) / (touchX as Float - centerX)).toDouble()))
        if (touchX as Float <= centerX && touchY as Float >= centerY){
            pinDegree += 180.0
        } else if (touchX as Float <= centerX && touchY as Float <= centerY){
            pinDegree += 180.0
        } else if (touchX as Float >= centerX && touchY as Float <= centerY){
            pinDegree += 360.0
        }
        val touchDistance = sqrt((touchX as Float - centerX).toDouble().pow(2.0) + (touchY as Float - centerY).toDouble().pow(2.0))
        var touchPin: Int? = null

        if (touchDistance <= 0.3 * radius && (abs(pinDegree - hourPinDegree) <= 6 || abs(pinDegree - hourPinDegree) >= 354)) {
            touchPin = 0    // hour pin
        } else if (touchDistance <= 0.5 * radius && (abs(pinDegree - minutePinDegree) <= 6 || abs(pinDegree - minutePinDegree) >= 354)) {
            touchPin = 1    // minute pin
        } else if (touchDistance <= 0.7 * radius && (abs(pinDegree - secondPinDegree) <= 6 || abs(pinDegree - secondPinDegree) >= 354)) {
            touchPin = 2    // second pin
        }

        manualUpdatePinDegree(touchPin, pinDegree)

        return true
    }
}