package com.danny.burge.wordsgame.helpers.utils

import com.danny.burge.wordsgame.app.WordsGameApp

fun getBlankString(length: Int = WordsGameApp.settings.gameDifficulty): List<String> {
    return (0 until length).map { " " }
}