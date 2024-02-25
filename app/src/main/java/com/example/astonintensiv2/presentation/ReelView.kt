package com.example.astonintensiv2.presentation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.astonintensiv2.R
import kotlin.random.Random

class ReelView(context: Context) : View(context) {
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var layout: FrameLayout? = null

    private val colors = listOf(
        getColorById(R.color.red),
        getColorById(R.color.orange),
        getColorById(R.color.yellow),
        getColorById(R.color.green),
        getColorById(R.color.blue),
        getColorById(R.color.dark_blue),
        getColorById(R.color.violet),
    )

    private val rect = RectF()

    private var animator: ObjectAnimator? = null
    private var scaleAnimator: ObjectAnimator? = null

    private fun getColorById(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        val sweepAngle = 360f / colors.size
        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(rect, sweepAngle * i, sweepAngle, true, paint)
        }
    }

    fun startRotating() {
        animator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()
            start()
        }
    }

    fun stopRotating() {
        animator?.cancel()
        animator = ObjectAnimator.ofFloat(
            this,
            "rotation",
            rotation,
            (rotation / 360).toInt() * 360f + Random.nextInt(360)
        ).apply {
            duration = 1000
            start()
        }
    }

    fun changeSize(progress: Float) {
        layout = this.parent as? FrameLayout
        scaleAnimator?.cancel()
        scaleAnimator = ObjectAnimator
            .ofFloat(this, "scaleX", scaleX, progress / 100).apply {
                duration = 100
                start()
            }
        scaleAnimator = ObjectAnimator
            .ofFloat(this, "scaleY", scaleY, progress / 100).apply {
                duration = 100
                start()
            }
    }

    fun getCurrentSegment(): Int {
        return ((rotation % 360) / (360f / colors.size)).toInt()
    }
}