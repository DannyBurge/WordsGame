package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.constants.NavigationFunc

@Composable
fun SettingsButton(modifier: Modifier, goToSettings: NavigationFunc) {
    ButtonWithImage(
        modifier = modifier,
        painter = painterResource(R.drawable.baseline_settings_black_24dp),
        onClick = goToSettings
    )
}