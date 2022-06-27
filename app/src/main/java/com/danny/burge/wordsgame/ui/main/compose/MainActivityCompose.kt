package com.danny.burge.wordsgame.ui.main.compose

import androidx.compose.runtime.Composable
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

    return navController
}


