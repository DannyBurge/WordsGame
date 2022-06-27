package com.danny.burge.wordsgame.ui.screens.settings.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.constants.NavigationFunc
import com.danny.burge.wordsgame.ui.elements.ButtonWithText
import com.danny.burge.wordsgame.ui.elements.SliderWithTextValue
import kotlin.properties.Delegates

var sliderValue by Delegates.notNull<Int>()

@Composable
fun SettingsScreenCompose(
    onGameSettingsChanged: (Int, Int) -> Unit,
    startNewGame: () -> Unit,
    navigateToMainScreen: NavigationFunc
) {
    sliderValue = WordsGameApp.gameDifficulty
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val (selector, applyButton) = createRefs()
        DifficultySelector(
            Modifier
                .wrapContentSize()
                .constrainAs(selector) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        ApplySettingsButton(
            Modifier
                .wrapContentSize()
                .constrainAs(applyButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(selector.bottom)
                },
            onGameSettingsChanged,
            navigateToMainScreen,
            startNewGame
        )
    }
}

@Composable
fun DifficultySelector(
    modifier: Modifier,
) {
    sliderValue = SliderWithTextValue(modifier, sliderValue) {}.toInt()
}

@Composable
fun ApplySettingsButton(
    modifier: Modifier,
    onDifficultyChanged: (Int, Int) -> Unit,
    navigateToMainScreen: NavigationFunc,
    startNewGame: () -> Unit
) {
    ButtonWithText(modifier, text = stringResource(id = R.string.applySettingsButton))
    {
        onDifficultyChanged(sliderValue, sliderValue)
        navigateToMainScreen()
        startNewGame()
    }
}
