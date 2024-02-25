package com.example.astonintensiv2.presentation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.astonintensiv2.model.ReelSegments

class ReelView(context: Context) : View(context) {
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }
    private val path = Path()
    private var currentRotation = 0f
    private var progress = 50

    private val segments = ReelSegments.get()

    private var animator: ObjectAnimator? = null
    private var scaleAnimator: ObjectAnimator? = null

    private fun getColorById(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2).toFloat() * (progress / 100f)
        val segmentAngle = 360f / segments.size
        val startAngle = -117f

        segments.forEachIndexed { index, segment ->
            paint.color = getColorById(segment.color)
            path.reset()
            path.addArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius,
                startAngle + segmentAngle * index + currentRotation,
                segmentAngle
            )
            path.lineTo(centerX, centerY)
            path.close()
            canvas.drawPath(path, paint)
        }
    }

    fun startRotating() {
        val randomDelay = (3000..6000).random().toLong()
        val handler = Handler(Looper.getMainLooper())
        animator = ObjectAnimator
            .ofFloat(this, "rotation", currentRotation, currentRotation + 360f)
            .apply {
                duration = 1000
                repeatMode = ValueAnimator.RESTART
                repeatCount = ObjectAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        handler.postDelayed({
            animator?.cancel()
            currentRotation = rotation % 360f
        }, randomDelay)
    }

    fun changeSize(progress: Int) {
        scaleAnimator?.cancel()
        this.progress = progress
        invalidate()
    }

    fun getCurrentSegment(): String {
        val segmentAngle = 360f / segments.size
        val normalizedRotation = (currentRotation + 360f) % 360f
        var topSegmentIndex = ((segments.size - ((normalizedRotation + 25) / segmentAngle).toInt()))
        if (topSegmentIndex >= segments.size) {
            topSegmentIndex = 0
        }
        return segments.getOrNull(topSegmentIndex)?.nameColor ?: "null"
    }
}