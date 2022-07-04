package com.danny.burge.wordsgame.constants

/*
String constants
*/

// Navigation
const val MAIN_SCREEN = "MainScreenCompose"
const val SETTINGS_SCREEN = "SettingsScreenCompose"

// Settings
const val SETTINGS_FILE_NAME = "settingsFile"
const val DIFFICULTY_VALUE = "difficultyValue"
const val ATTEMPT_VALUE = "attemptValue"

const val WIKIPEDIA_URL = "https://ru.wikipedia.org/w/"

// Tags
const val DEBUG_LOG_TAG = "WordsGameDebugTag"

/*
Integer constants
*/
const val DIFFICULTY_DEFAULT = 5
const val ATTEMPT_NUMBER_DEFAULT = DIFFICULTY_DEFAULT
const val LETTER_ON_SPOT_CODE = 2
const val LETTER_IN_WORD_CODE = 1
const val LETTER_NOT_IN_WORD_CODE = 0
/*
Alias constants
*/
typealias NavigationFunc = () -> Unit