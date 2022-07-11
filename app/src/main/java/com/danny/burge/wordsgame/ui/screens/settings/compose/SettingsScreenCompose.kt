package com.danny.burge.wordsgame.ui.screens.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.NavigationCommand
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.logic.GameEvent
import com.danny.burge.wordsgame.ui.elements.SettingBox
import com.danny.burge.wordsgame.ui.elements.TopAppBar
import com.danny.burge.wordsgame.ui.elements.buttons.ApplySettingsButton
import com.danny.burge.wordsgame.ui.elements.checkBoxWithText
import com.danny.burge.wordsgame.ui.elements.sliderWithTextValue
import kotlin.properties.Delegates

var difficultySliderValue by Delegates.notNull<Int>()
var attemptsSliderValue by Delegates.notNull<Int>()
var keyboardVisibilityCheckBoxValue by Delegates.notNull<Boolean>()

@Composable
fun SettingsScreenCompose(
    onEventHandler: (GameEvent) -> Unit,
    onNavigationCommand: (NavigationCommand) -> Unit,
) {
    initSettingsValues()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            modifier = Modifier,
            isMainScreen = false,
            onNavigationCommand = onNavigationCommand
        )

        SettingBox(settingsName = stringResource(id = R.string.difficultySliderName)) {
            DifficultySelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

        SettingBox(settingsName = stringResource(id = R.string.attemptsSliderName)) {
            AttemptSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

        SettingBox(settingsName = stringResource(id = R.string.keyboardVisibilityCheckBoxName)) {
            KeyboardVisibilityCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

        ApplySettingsButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth()
                .wrapContentHeight(),
            onClick = {
                onEventHandler(
                    GameEvent.OnSettingsChange(
                        difficultySliderValue = difficultySliderValue,
                        attemptsSliderValue = attemptsSliderValue,
                        keyboardVisibilityCheckBoxValue = keyboardVisibilityCheckBoxValue
                    )
                )
                onEventHandler(GameEvent.OnNewGameClicked)
            }
        )
    }
}

private fun initSettingsValues() {
    WordsGameApp.settings.apply {
        difficultySliderValue = gameDifficulty
        attemptsSliderValue = attemptNumber
        keyboardVisibilityCheckBoxValue = hideKeysInKeyboard
    }
}

@Composable
fun DifficultySelector(
    modifier: Modifier,
) {
    difficultySliderValue = sliderWithTextValue(
        modifier = modifier,
        range = 3F..7F,
        defaultValue = difficultySliderValue
    ) {}.toInt()
}

@Composable
fun AttemptSelector(
    modifier: Modifier,
) {
    attemptsSliderValue = sliderWithTextValue(
        modifier = modifier,
        range = 3F..20F,
        defaultValue = attemptsSliderValue
    ) {}.toInt()
}

@Composable
fun KeyboardVisibilityCheckBox(
    modifier: Modifier,
) {
    keyboardVisibilityCheckBoxValue =
        checkBoxWithText(
            modifier = modifier,
            text = stringResource(id = R.string.keyboardVisibilityCheckBoxMessage),
            defaultValue = keyboardVisibilityCheckBoxValue
        )
}
