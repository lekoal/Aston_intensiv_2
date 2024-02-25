package com.example.astonintensiv2.model

import com.example.astonintensiv2.R

object ReelSegments {
    fun get(): List<ReelSegment> = listOf(
        ReelSegment(
            nameColor = "Red",
            color = R.color.red
        ),
        ReelSegment(
            nameColor = "Orange",
            color = R.color.orange
        ),
        ReelSegment(
            nameColor = "Yellow",
            color = R.color.yellow
        ),
        ReelSegment(
            nameColor = "Green",
            color = R.color.green
        ),
        ReelSegment(
            nameColor = "Blue",
            color = R.color.blue
        ),
        ReelSegment(
            nameColor = "DarkBlue",
            color = R.color.dark_blue
        ),
        ReelSegment(
            nameColor = "Violet",
            color = R.color.violet
        )
    )
}