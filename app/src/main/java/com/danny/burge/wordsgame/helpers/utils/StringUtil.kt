package com.danny.burge.wordsgame.helpers.utils

import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.EMPTY_LETTER
import com.danny.burge.wordsgame.constants.NO_LETTER

fun getBlankString(length: Int = WordsGameApp.settings.gameDifficulty): List<String> {
    return (0 until length).map { NO_LETTER }
}