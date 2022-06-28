package com.danny.burge.wordsgame.ui.main.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danny.burge.wordsgame.constants.MAIN_SCREEN
import com.danny.burge.wordsgame.constants.NavigationFunc
import com.danny.burge.wordsgame.constants.SETTINGS_SCREEN
import com.danny.burge.wordsgame.ui.screens.main.MainScreenViewModel
import com.danny.burge.wordsgame.ui.screens.main.compose.MainScreenCompose
import com.danny.burge.wordsgame.ui.screens.settings.SettingsScreenViewModel
import com.danny.burge.wordsgame.ui.screens.settings.compose.SettingsScreenCompose

@Composable
fun GameUI(
    mainScreenViewModel: MainScreenViewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    startNewGame: () -> Unit,
    closeApp: () -> Unit,
    goToSettings: NavigationFunc,
    goToMainScreen: NavigationFunc
): NavController {
    val navController = rememberNavController()
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        NavHost(navController = navController, startDestination = MAIN_SCREEN) {
            composable(MAIN_SCREEN) {
                MainScreenCompose(
                    liveDataAnswers = mainScreenViewModel.answers,
                    liveDataSecretWord = mainScreenViewModel.currentSecretWord,
                    navigateToToSettings = goToSettings,
                    startNewGame = { startNewGame() },
                    closeApp = { closeApp() },
                    checkWord = mainScreenViewModel::checkWord
                )
            }
            composable(SETTINGS_SCREEN) {
                SettingsScreenCompose(
                    onGameSettingsChanged = settingsScreenViewModel::onGameSettingsChanged,
                    navigateToMainScreen = goToMainScreen,
                    startNewGame = { startNewGame() }
                )
            }
        }
    }
    return navController
}


