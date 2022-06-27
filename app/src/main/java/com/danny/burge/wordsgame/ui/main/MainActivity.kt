package com.danny.burge.wordsgame.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.constants.MAIN_SCREEN
import com.danny.burge.wordsgame.constants.SETTINGS_SCREEN
import com.danny.burge.wordsgame.ui.main.compose.GameUI
import com.danny.burge.wordsgame.ui.screens.main.MainScreenViewModel
import com.danny.burge.wordsgame.ui.screens.main.compose.attempt
import com.danny.burge.wordsgame.ui.screens.settings.SettingsScreenViewModel
import com.danny.burge.wordsgame.ui.theme.WordsGameTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    lateinit var navController: NavController

    private val mainScreenViewModel: MainScreenViewModel by viewModel()
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsScreenViewModel.onAppOpen()

        setContent {
            WordsGameTheme {
                navController =
                    GameUI(
                        mainScreenViewModel,
                        settingsScreenViewModel,
                        ::startGame,
                        ::closeApp,
                        ::goToSettingsScreen,
                        ::goToMainScreen,
                    )
            }
        }
        startGame()
    }

    private fun startGame() {
        Log.d(DEBUG_LOG_TAG, "startGame")
        attempt = 0
        mainScreenViewModel.startGame()
    }

    private fun closeApp() {
        settingsScreenViewModel.onAppClosed()
        finishAffinity()
    }

    private fun goToSettingsScreen() {
        navController.navigate(SETTINGS_SCREEN)
    }

    private fun goToMainScreen() {
        navController.navigate(MAIN_SCREEN)
    }
}