package com.danny.burge.wordsgame.ui.model

data class Answer(
    var word: String,
    var colorMask: List<ColorMask>,
    var isCompletelyOpen: Boolean,
    val attempt: Int
)

enum class ColorMask {
    LETTER_ON_SPOT,
    LETTER_IN_WORD,
    LETTER_NOT_IN_WORD
}
