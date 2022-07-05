package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danny.burge.wordsgame.R

@Composable
fun StartNewGameButton(modifier: Modifier, startNewGame: () -> Unit) {
    ButtonWithText(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        text = stringResource(id = R.string.startNewGame)
    ) {
        startNewGame()
    }
}