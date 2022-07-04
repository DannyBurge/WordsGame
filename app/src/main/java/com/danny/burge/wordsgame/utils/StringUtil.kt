package com.danny.burge.wordsgame.utils

import com.danny.burge.wordsgame.WordsGameApp

fun getBlankString(length: Int = WordsGameApp.gameDifficulty): List<String> {
    return (0 until length).map { " " }
}