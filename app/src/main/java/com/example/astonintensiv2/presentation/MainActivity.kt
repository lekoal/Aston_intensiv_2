package com.example.astonintensiv2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.astonintensiv2.databinding.ActivityMainBinding
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
        val currentSegment = reelView.getCurrentSegment()
        if (currentSegment != null) {
            when (currentSegment.action) {
                "Text" -> {
                    textCustomView.setText(currentSegment.content)
                }

                "Image" -> {
                    binding.imageField.load(currentSegment.content) {
                        crossfade(true)
                        crossfade(100)
                    }
                }
            }
        }
    }

    private fun fieldsClear() {
        binding.imageField.load(null)
        textCustomView.setText("")
    }
}