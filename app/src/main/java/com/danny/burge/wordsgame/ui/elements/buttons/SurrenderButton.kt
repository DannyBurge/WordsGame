package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.ATTEMPT_VALUE_SURRENDER

@Composable
fun SurrenderButton(modifier: Modifier, onClick: () -> Unit) {
    ButtonWithText(
        modifier = modifier,
        text = stringResource(id = R.string.surrenderButton),
        onClick = onClick
    )
}