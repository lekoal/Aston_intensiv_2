package com.example.astonintensiv2.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.astonintensiv2.databinding.ActivityMainBinding
import com.example.astonintensiv2.widget.VerticalSeekBar

class MainActivity : AppCompatActivity() {
    private lateinit var reelView: ReelView
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reelView = ReelView(this)
        binding.reelLayout.post {
            val params = binding.reelLayout.layoutParams
            params.height = binding.reelLayout.width
            binding.reelLayout.layoutParams = params
        }
        binding.reelView.addView(reelView)

        binding.btnStart.setOnClickListener {
            reelView.startRotating()
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
            Log.i("MainActivity", reelView.getCurrentSegment())
        }
    }
}