package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.NavigationCommand
import com.danny.burge.wordsgame.constants.SETTINGS_SCREEN
import com.danny.burge.wordsgame.helpers.extention.visible
import com.danny.burge.wordsgame.ui.elements.buttons.BackButton
import com.danny.burge.wordsgame.ui.elements.buttons.SettingsButton
import com.danny.burge.wordsgame.ui.theme.letterStyle

@Composable
fun TopAppBar(
    modifier: Modifier,
    isMainScreen: Boolean,
    onNavigationCommand: (NavigationCommand) -> Unit,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onBackground)
            .padding(8.dp)
    ) {
        BackButton(
            modifier = Modifier
                .wrapContentSize()
                .visible(!isMainScreen)
                .align(Alignment.CenterVertically),
            goBack = {
                if (!isMainScreen) onNavigationCommand(NavigationCommand.Back)
            }
        )

        Text(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.app_name).uppercase(),
            style = letterStyle
        )

        SettingsButton(
            modifier = Modifier
                .visible(isMainScreen)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .align(Alignment.CenterVertically),
            goToSettings = {
                if (isMainScreen) onNavigationCommand(NavigationCommand.ByRoute(SETTINGS_SCREEN))
            }
        )
    }
}