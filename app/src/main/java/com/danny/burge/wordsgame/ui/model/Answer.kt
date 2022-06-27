package com.danny.burge.wordsgame.ui.model

data class Answer(
    var word: String,
    var colorMask: List<Int>,
    var isCompletelyOpen: Boolean,
    val attempt: Int
)
