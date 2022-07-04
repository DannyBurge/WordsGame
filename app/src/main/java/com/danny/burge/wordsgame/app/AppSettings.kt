package com.danny.burge.wordsgame.app

import com.danny.burge.wordsgame.constants.ATTEMPT_NUMBER_DEFAULT
import com.danny.burge.wordsgame.constants.DIFFICULTY_DEFAULT

data class AppSettings(
    var gameDifficulty: Int = DIFFICULTY_DEFAULT,
    var attemptNumber: Int = ATTEMPT_NUMBER_DEFAULT,
    var keyboardVisibility: Boolean = false
)
