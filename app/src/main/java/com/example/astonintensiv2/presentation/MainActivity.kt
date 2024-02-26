package com.example.astonintensiv2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.astonintensiv2.databinding.ActivityMainBinding
import com.example.astonintensiv2.model.ReelSegment
import com.example.astonintensiv2.widget.VerticalSeekBar

class MainActivity : AppCompatActivity() {
    private lateinit var reelView: ReelView
    private lateinit var binding: ActivityMainBinding
    private lateinit var textCustomView: TextCustomView
    private var customViewText = ""
    private var imageUrl = ""
    private var currentRotation = 0f
    private var currentProgress = 50
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reelView = ReelView(this)
        textCustomView = TextCustomView(this)
        binding.reelLayout.post {
            val params = binding.reelLayout.layoutParams
            params.height = binding.reelLayout.width
            binding.reelLayout.layoutParams = params
        }
        binding.reelView.addView(reelView)
        binding.textField.addView(textCustomView)

        binding.btnStart.setOnClickListener {
            reelStarting()
        }

        binding.reelScale.setOnSeekBarChangeListener(
            object : VerticalSeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: VerticalSeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    reelView.changeSize(progress)
                }

            }
        )

        binding.btnReset.setOnClickListener {
            fieldsClear()
        }

        binding.reelView.setOnClickListener {
            reelStarting()
        }

        if (savedInstanceState != null) {
            currentProgress = savedInstanceState.getInt("progress")
            currentRotation = savedInstanceState.getFloat("rotation")
            customViewText = savedInstanceState.getString("text", "")
            imageUrl = savedInstanceState.getString("url", "")
        }
    }

    private fun getCurrentSegment() {
        startButtonDisabler(isEnable = true)
        binding.reelView.isClickable = true
        val currentSegment = reelView.getCurrentSegment()
        if (currentSegment != null) {
            segmentAction(currentSegment)
        }
    }

    private fun startButtonDisabler(isEnable: Boolean) {
        binding.btnStart.isEnabled = isEnable
    }

    private fun reelStarting() {
        binding.reelView.isClickable = false
        startButtonDisabler(isEnable = false)
        fieldsClear()
        reelView.startRotating {
            getCurrentSegment()
        }
    }

    private fun segmentAction(segment: ReelSegment) {
        when (segment.action) {
            "Text" -> {
                imageUrl = ""
                customViewText = segment.content
                textCustomView.setText(customViewText)
            }

            "Image" -> {
                customViewText = ""
                imageUrl = segment.content
                setImage(imageUrl)
            }
        }
    }

    private fun setImage(url: String) {
        binding.imageField.load(url) {
            crossfade(true)
            crossfade(100)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat("rotation", reelView.getCurrentRotation())
        outState.putInt("progress", binding.reelScale.getProgress())
        outState.putString("text", customViewText)
        outState.putString("url", imageUrl)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.reelScale.setProgress(currentProgress)
        reelView.setCurrentRotation(currentRotation)
        reelView.changeSize(currentProgress)
        textCustomView.setText(customViewText)
        setImage(imageUrl)
    }

    private fun fieldsClear() {
        binding.imageField.load(null)
        textCustomView.setText("")
        customViewText = ""
        imageUrl = ""
    }
}