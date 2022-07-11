package com.danny.burge.wordsgame.logic

sealed class GameEvent {
    data class OnInput(
        val index: Int,
        val input: String
    ) : GameEvent()

    data class OnTileFocused(
        val index: Int,
    ) : GameEvent()

    data class OnSettingsChange(
        val difficultySliderValue: Int,
        val attemptsSliderValue: Int,
        val keyboardVisibilityCheckBoxValue: Boolean
    ) : GameEvent()

    object OnSnackBarAddPressed : GameEvent()
    object OnWordApplied : GameEvent()
    object OnNewGameClicked : GameEvent()
    object OnGameEnded : GameEvent()
    object OnApplicationStarted : GameEvent()
    object OnApplicationClosed : GameEvent()
}