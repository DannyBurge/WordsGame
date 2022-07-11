package com.danny.burge.wordsgame.di.model

import android.content.Context
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.*

class PrefsHelper(context: Context) {
    private val prefs = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)

    fun getSettings() {
        WordsGameApp.settings.apply {
            gameDifficulty = prefs.getInt(DIFFICULTY_VALUE, DIFFICULTY_DEFAULT)
            attemptNumber = prefs.getInt(ATTEMPT_VALUE, ATTEMPT_NUMBER_DEFAULT)
            hideKeysInKeyboard = prefs.getBoolean(KEYBOARD_VISIBILITY, HIDE_KEYS_IN_KEYBOARD_DEFAULT)
        }
    }
    fun saveSettings() {
        WordsGameApp.settings.apply {
            prefs.edit().putInt(DIFFICULTY_VALUE, gameDifficulty).apply()
            prefs.edit().putInt(ATTEMPT_VALUE, attemptNumber).apply()
            prefs.edit().putBoolean(KEYBOARD_VISIBILITY, hideKeysInKeyboard).apply()
        }
    }
}