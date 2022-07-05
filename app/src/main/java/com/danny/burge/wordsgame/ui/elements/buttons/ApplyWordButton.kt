package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.danny.burge.wordsgame.R

@Composable
fun ApplyWordButton(modifier: Modifier, enabled: Boolean, checkWord: () -> Unit) {
    val focusManager = LocalFocusManager.current
    ButtonWithText(modifier, text = stringResource(id = R.string.applyWordButton), enabled,
        onClick = {
            focusManager.clearFocus()
            checkWord()
        }
    )
}