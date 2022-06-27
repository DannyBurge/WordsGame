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

// Tags
const val DEBUG_LOG_TAG = "WordsGameDebugTag"

/*
Integer constants
*/
const val DIFFICULTY_DEFAULT = 5
const val ATTEMPT_NUMBER_DEFAULT = DIFFICULTY_DEFAULT
const val LETTER_FULL_OPEN = 2
/*
Alias constants
*/
typealias NavigationFunc = () -> Unit