package com.example.astonintensiv2.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class VerticalSeekBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var progress = 50
    private var max = 100
    private var onSeekBarChangeListener: OnSeekBarChangeListener? = null

    private val linePaint = Paint()
    private val thumbPaintOuter = Paint()
    private val thumbPaintInner = Paint()

    init {
        linePaint.color = Color.MAGENTA
        linePaint.strokeWidth = 8f
        thumbPaintOuter.color = Color.RED
        thumbPaintOuter.strokeWidth = 6f
        thumbPaintOuter.style = Paint.Style.STROKE
        thumbPaintInner.color = Color.MAGENTA
        thumbPaintInner.style = Paint.Style.FILL
    }

    interface OnSeekBarChangeListener {
        fun onProgressChanged(seekBar: VerticalSeekBar, progress: Int, fromUser: Boolean)
    }

    fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener) {
        onSeekBarChangeListener = listener
    }

    fun setMax(max: Int) {
        this.max = max
    }

    private fun setProgress(progress: Int) {
        if (progress in 0..max) {
            this.progress = progress
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val thumbSize = 30f
        val thumbY = (height - thumbSize * 2) * (1 - progress.toFloat() / max) + thumbSize

        canvas.drawLine(width / 2, 0f, width / 2, height, linePaint)
        canvas.drawCircle(
            width / 2,
            thumbY,
            thumbSize - thumbPaintOuter.strokeWidth + thumbPaintOuter.strokeWidth / 2,
            thumbPaintInner
        )
        canvas.drawCircle(width / 2, thumbY, thumbSize, thumbPaintOuter)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN || event?.action == MotionEvent.ACTION_MOVE) {
            val y = event.y.coerceIn(0f, height.toFloat())
            val newProgress = ((1 - y / height) * max).toInt()
            setProgress(newProgress)
            onSeekBarChangeListener?.onProgressChanged(this, newProgress, true)
            return true
        }
        return super.onTouchEvent(event)
    }
}