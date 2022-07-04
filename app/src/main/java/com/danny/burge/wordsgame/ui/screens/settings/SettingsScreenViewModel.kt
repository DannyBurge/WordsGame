package com.danny.burge.wordsgame.ui.screens.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.*

class SettingsScreenViewModel(private val context: Context) : ViewModel() {

    fun onAppOpen() {
        Log.d(DEBUG_LOG_TAG, "onAppOpen")
        getSavedSettings()
    }

    fun onGameSettingsChanged(
        newGameDifficulty: Int,
        newAttemptNumber: Int,
        newKeyboardVisibility: Boolean
    ) {
        Log.d(DEBUG_LOG_TAG, "onGameSettingsChanged")
        WordsGameApp.settings.apply {
            gameDifficulty = newGameDifficulty
            attemptNumber = newAttemptNumber
            keyboardVisibility = newKeyboardVisibility
        }
        saveSettings()
    }

    fun onAppClosed() {
        Log.d(DEBUG_LOG_TAG, "onAppClosed")
        saveSettings()
    }

    private fun getSavedSettings() {
        val prefs = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        WordsGameApp.settings.apply {
            gameDifficulty = prefs.getInt(DIFFICULTY_VALUE, DIFFICULTY_DEFAULT)
            attemptNumber = prefs.getInt(ATTEMPT_VALUE, ATTEMPT_NUMBER_DEFAULT)
            keyboardVisibility = prefs.getBoolean(KEYBOARD_VISIBILITY, KEYBOARD_VISIBILITY_DEFAULT)
        }
    }

    private fun saveSettings() {
        val prefs = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        WordsGameApp.settings.apply {
            prefs.edit().putInt(DIFFICULTY_VALUE, gameDifficulty).apply()
            prefs.edit().putInt(ATTEMPT_VALUE, attemptNumber).apply()
            prefs.edit().putBoolean(KEYBOARD_VISIBILITY, keyboardVisibility).apply()
        }
    }
}