package com.danny.burge.wordsgame.ui.screens.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.constants.*

class SettingsScreenViewModel(private val context: Context) : ViewModel() {

    fun onAppOpen() {
        Log.d(DEBUG_LOG_TAG, "onAppOpen")
        getSavedSettings()
    }

    fun onGameSettingsChanged(gameDifficulty: Int, attemptNumber: Int) {
        Log.d(DEBUG_LOG_TAG, "onGameSettingsChanged")
        WordsGameApp.gameDifficulty = gameDifficulty
        WordsGameApp.attemptNumber = attemptNumber
    }

    fun onAppClosed() {
        Log.d(DEBUG_LOG_TAG, "onAppClosed")
        saveSettings()
    }

    private fun getSavedSettings() {
        val prefs = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        WordsGameApp.gameDifficulty = prefs.getInt(DIFFICULTY_VALUE, DIFFICULTY_DEFAULT)
        WordsGameApp.attemptNumber = prefs.getInt(ATTEMPT_VALUE, ATTEMPT_NUMBER_DEFAULT)
    }

    private fun saveSettings() {
        val prefs = context.getSharedPreferences(SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(DIFFICULTY_VALUE, WordsGameApp.gameDifficulty).apply()
        prefs.edit().putInt(ATTEMPT_VALUE, WordsGameApp.attemptNumber).apply()
    }
}