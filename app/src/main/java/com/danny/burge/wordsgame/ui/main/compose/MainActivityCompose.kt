package com.danny.burge.wordsgame.ui.main.compose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danny.burge.wordsgame.app.AppNavigator
import com.danny.burge.wordsgame.constants.MAIN_SCREEN
import com.danny.burge.wordsgame.constants.SETTINGS_SCREEN
import com.danny.burge.wordsgame.logic.GameEvent
import com.danny.burge.wordsgame.ui.screens.main.compose.MainScreenCompose
import com.danny.burge.wordsgame.ui.screens.settings.compose.SettingsScreenCompose

@Composable
fun GameUI(
    onEventHandler: (GameEvent) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN
    ) {
        composable(MAIN_SCREEN) {
            MainScreenCompose(
                onNavigationCommand = {
                    AppNavigator.navigate(navController, it)
                },
                onEventHandler = onEventHandler
            )
        }
        composable(SETTINGS_SCREEN) {
            SettingsScreenCompose(
                onNavigationCommand = {
                    AppNavigator.navigate(navController, it)
                },
                onEventHandler = onEventHandler
            )
        }
    }
}


