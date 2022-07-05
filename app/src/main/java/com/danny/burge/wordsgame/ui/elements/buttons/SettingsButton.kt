package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danny.burge.wordsgame.constants.NavigationFunc

@Composable
fun SettingsButton(modifier: Modifier, goToSettings: NavigationFunc) {
    ButtonWithImage(modifier = modifier, onClick = goToSettings)
}