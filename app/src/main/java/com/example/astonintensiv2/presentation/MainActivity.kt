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
            startButtonDisabler(isEnable = false)
            fieldsClear()
            reelView.startRotating {
                getCurrentSegment()
            }
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
    }

    private fun getCurrentSegment() {
        startButtonDisabler(isEnable = true)
        val currentSegment = reelView.getCurrentSegment()
        if (currentSegment != null) {
            segmentAction(currentSegment)
        }
    }

    private fun startButtonDisabler(isEnable: Boolean) {
        binding.btnStart.isEnabled = isEnable
    }
    private fun segmentAction(segment: ReelSegment) {
        when (segment.action) {
            "Text" -> {
                textCustomView.setText(segment.content)
            }

            "Image" -> {
                binding.imageField.load(segment.content) {
                    crossfade(true)
                    crossfade(100)
                }
            }
        }
    }

    private fun fieldsClear() {
        binding.imageField.load(null)
        textCustomView.setText("")
    }
}