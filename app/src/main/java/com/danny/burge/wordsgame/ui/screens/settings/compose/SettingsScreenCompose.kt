package com.danny.burge.wordsgame.ui.screens.settings.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.NavigationFunc
import com.danny.burge.wordsgame.ui.elements.ButtonWithText
import com.danny.burge.wordsgame.ui.elements.SliderWithTextValue
import kotlin.properties.Delegates

var difficultySliderValue by Delegates.notNull<Int>()
var attemptsSliderValue by Delegates.notNull<Int>()
var keyboardVisibilityCheckBoxValue by Delegates.notNull<Boolean>()

@Composable
fun SettingsScreenCompose(
    onGameSettingsChanged: (Int, Int, Boolean) -> Unit,
    startNewGame: () -> Unit,
    navigateToMainScreen: NavigationFunc
) {
    initSettingsValues()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth()
    ) {
        DifficultySelector(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        AttemptSelector(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
        ApplySettingsButton(
            Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth()
                .wrapContentHeight(),
            onGameSettingsChanged,
            navigateToMainScreen,
            startNewGame
        )
    }
}

private fun initSettingsValues() {
    WordsGameApp.settings.apply {
        difficultySliderValue = gameDifficulty
        attemptsSliderValue = attemptNumber
        keyboardVisibilityCheckBoxValue = keyboardVisibility
    }
}

@Composable
fun DifficultySelector(
    modifier: Modifier,
) {
    difficultySliderValue = SliderWithTextValue(
        modifier = modifier,
        range = 3F..7F,
        defaultValue = difficultySliderValue
    ) {}.toInt()
}

@Composable
fun AttemptSelector(
    modifier: Modifier,
) {
    attemptsSliderValue = SliderWithTextValue(
        modifier = modifier,
        range = 3F..20F,
        defaultValue = attemptsSliderValue
    ) {}.toInt()
}

@Composable
fun ApplySettingsButton(
    modifier: Modifier,
    onDifficultyChanged: (Int, Int, Boolean) -> Unit,
    navigateToMainScreen: NavigationFunc,
    startNewGame: () -> Unit
) {
    ButtonWithText(modifier, text = stringResource(id = R.string.applySettingsButton))
    {
        onDifficultyChanged(difficultySliderValue, attemptsSliderValue, keyboardVisibilityCheckBoxValue)
        navigateToMainScreen()
        startNewGame()
    }
}
