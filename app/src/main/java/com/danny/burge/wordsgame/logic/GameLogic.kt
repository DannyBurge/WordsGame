package com.danny.burge.wordsgame.logic

import android.content.Context
import android.util.Log
import com.danny.burge.wordsgame.app.AppNavigator
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.BACKSPACE_LETTER
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.constants.EMPTY_LETTER
import com.danny.burge.wordsgame.ui.main.MainActivity
import com.danny.burge.wordsgame.ui.screens.main.MainScreenViewModel
import com.danny.burge.wordsgame.ui.screens.settings.SettingsScreenViewModel

class GameLogic(
    private val context: Context,
    private val mainScreenViewModel: MainScreenViewModel,
    private val settingsScreenViewModel: SettingsScreenViewModel,
) : BaseLogic<GameEvent> {
    override fun onEvent(gameEvent: GameEvent) {
        when (gameEvent) {
            is GameEvent.OnInput -> onInput(gameEvent.index, gameEvent.input)
            is GameEvent.OnTileFocused -> onTileFocused(gameEvent.index)
            is GameEvent.OnSettingsChange -> onSettingsChange(
                gameEvent.difficultySliderValue,
                gameEvent.attemptsSliderValue,
                gameEvent.keyboardVisibilityCheckBoxValue
            )
            GameEvent.OnSnackBarAddPressed -> onSnackBarAddPressed()
            GameEvent.OnWordApplied -> onWordApplied()
            GameEvent.OnNewGameClicked -> onNewGameClicked()
            GameEvent.OnGameEnded -> onGameEnded()
            GameEvent.OnApplicationStarted -> onApplicationStarted()
            GameEvent.OnApplicationClosed -> onApplicationClosed()
        }
    }

    private fun onInput(index: Int, input: String) {
        Log.d(DEBUG_LOG_TAG, "onInput $input at $index")
        WordsGameApp.state.secretWordAnswer[index] = input
    }

    private fun onTileFocused(index: Int) {
        Log.d(DEBUG_LOG_TAG, "onTileFocused $index")
        WordsGameApp.state.cellIndex = index
    }

    private fun onWordApplied() {
        Log.d(DEBUG_LOG_TAG, "OnWordApplied")
        mainScreenViewModel.checkWord()
    }

    private fun onSnackBarAddPressed() {
        mainScreenViewModel.addWordToDatabase()
        mainScreenViewModel.updateDataBase()
    }

    private fun onNewGameClicked() {
        Log.d(DEBUG_LOG_TAG, "onNewGameClicked")
        mainScreenViewModel.startGame()
        WordsGameApp.state.showDialog.value = false
    }

    private fun onGameEnded() {
        Log.d(DEBUG_LOG_TAG, "OnGameEnded")
        WordsGameApp.state.showDialog.value = true
    }

    private fun onSettingsChange(
        difficultySliderValue: Int,
        attemptsSliderValue: Int,
        keyboardVisibilityCheckBoxValue: Boolean
    ) {
        Log.d(DEBUG_LOG_TAG, "OnSettingsChange")

        settingsScreenViewModel.onGameSettingsChanged(
            difficultySliderValue,
            attemptsSliderValue,
            keyboardVisibilityCheckBoxValue
        )
    }

    private fun onApplicationStarted() {
        Log.d(DEBUG_LOG_TAG, "OnApplicationStarted")
        settingsScreenViewModel.getSavedSettings()
        onNewGameClicked()
    }

    private fun onApplicationClosed() {
        Log.d(DEBUG_LOG_TAG, "OnApplicationClosed")

        settingsScreenViewModel.saveSettings()
        WordsGameApp.state.showDialog.value = false
        (context as MainActivity).finishAffinity()
    }
}