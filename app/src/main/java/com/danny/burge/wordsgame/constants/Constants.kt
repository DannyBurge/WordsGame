package com.danny.burge.wordsgame.constants

/**
String constants
 **/
// General
const val EMPTY_LETTER = " "
const val BACKSPACE_LETTER = "<"

// Navigation
const val MAIN_SCREEN = "MainScreenCompose"
const val SETTINGS_SCREEN = "SettingsScreenCompose"

// Settings
const val SETTINGS_FILE_NAME = "settingsFile"
const val DIFFICULTY_VALUE = "difficultyValue"
const val ATTEMPT_VALUE = "attemptValue"
const val KEYBOARD_VISIBILITY = "keyboardVisibility"

const val WIKIPEDIA_URL = "https://ru.wikipedia.org/w/"

/**
Tags
 **/
const val DEBUG_LOG_TAG = "WordsGameDebugTag"

/**
Integer constants
 **/
const val DIFFICULTY_DEFAULT = 5
const val ATTEMPT_NUMBER_DEFAULT = DIFFICULTY_DEFAULT
const val ATTEMPT_VALUE_SURRENDER = -1

/**
Boolean constants
 **/
const val KEYBOARD_VISIBILITY_DEFAULT = false

/**
Alias
 **/
typealias NavigationFunc = () -> Unit