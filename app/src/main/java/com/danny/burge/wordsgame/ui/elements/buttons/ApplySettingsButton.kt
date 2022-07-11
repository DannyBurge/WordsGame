package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danny.burge.wordsgame.R

@Composable
fun ApplySettingsButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    ButtonWithText(modifier, text = stringResource(id = R.string.applySettingsButton))
    {
        onClick()
    }
}