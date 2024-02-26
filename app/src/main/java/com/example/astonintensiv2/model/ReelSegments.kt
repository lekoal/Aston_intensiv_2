package com.example.astonintensiv2.model

import com.example.astonintensiv2.R

object ReelSegments {
    fun get(): List<ReelSegment> = listOf(
        ReelSegment(
            nameColor = "Red",
            color = R.color.red,
            action = "Text",
            content = "Red color"
        ),
        ReelSegment(
            nameColor = "Orange",
            color = R.color.orange,
            action = "Image",
            content = "https://loremflickr.com/240/320/orange,hair,girl/all"
        ),
        ReelSegment(
            nameColor = "Yellow",
            color = R.color.yellow,
            action = "Text",
            content = "Yellow color"
        ),
        ReelSegment(
            nameColor = "Green",
            color = R.color.green,
            action = "Image",
            content = "https://loremflickr.com/240/320/green,hair,girl/all"
        ),
        ReelSegment(
            nameColor = "Blue",
            color = R.color.blue,
            action = "Text",
            content = "Blue color"
        ),
        ReelSegment(
            nameColor = "DarkBlue",
            color = R.color.dark_blue,
            action = "Image",
            content = "https://loremflickr.com/240/320/darkblue,hair,girl/all"
        ),
        ReelSegment(
            nameColor = "Violet",
            color = R.color.violet,
            action = "Text",
            content = "Violet color"
        )
    )
}