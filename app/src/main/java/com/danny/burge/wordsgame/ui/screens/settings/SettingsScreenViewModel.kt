package com.danny.burge.wordsgame.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.di.model.PrefsHelper

class SettingsScreenViewModel(private val prefsHelper: PrefsHelper) : ViewModel() {

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
            hideKeysInKeyboard = newKeyboardVisibility
        }
        saveSettings()
    }

    fun getSavedSettings() {
        prefsHelper.getSettings()
    }

    fun saveSettings() {
        prefsHelper.saveSettings()
    }
}