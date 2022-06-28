package com.danny.burge.wordsgame.ui.model

data class KeyboardKey(
    val label: String,
    val onSpot: Boolean? = null,
    val inWord: Boolean? = null,
    val notInWord: Boolean? = null
)
