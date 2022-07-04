package com.danny.burge.wordsgame.ui.model

data class KeyboardKey(
    val label: String,
    var onSpot: Boolean = false,
    var inWord: Boolean = false,
    var notInWord: Boolean = false,
)
