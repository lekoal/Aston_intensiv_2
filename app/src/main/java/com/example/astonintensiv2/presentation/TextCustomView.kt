package com.example.astonintensiv2.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import com.example.astonintensiv2.R

class TextCustomView(context: Context) : View(context) {
    private var currentText = ""

    fun setText(text: String) {
        currentText = text
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val textX = width / 2f
        val textY = height / 2f

        canvas.drawText(currentText, textX, textY, createPaint())
        super.onDraw(canvas)
    }

    private fun createPaint(): Paint {
        return Paint().apply {
            textSize = 50f
            textAlign = Paint.Align.CENTER
            color = colorSelector()
        }
    }

    private fun colorSelector(): Int {
        return when (currentText) {
            "Red color" -> {
                getColorById(R.color.red)
            }
            "Orange color" -> {
                getColorById(R.color.orange)
            }
            "Yellow color" -> {
                getColorById(R.color.yellow)
            }
            "Green color" -> {
                getColorById(R.color.green)
            }
            "Blue color" -> {
                getColorById(R.color.blue)
            }
            "DarkBlue color" -> {
                getColorById(R.color.dark_blue)
            }
            "Violet color" -> {
                getColorById(R.color.violet)
            }
            else -> {
                getColorById(R.color.black)
            }
        }
    }

    private fun getColorById(id: Int): Int {
        return ContextCompat.getColor(context, id)
    }
}