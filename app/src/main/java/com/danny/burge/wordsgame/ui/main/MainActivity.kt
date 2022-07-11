package com.danny.burge.wordsgame.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.findNavController
import com.danny.burge.wordsgame.app.AppNavigator
import com.danny.burge.wordsgame.app.NavigationCommand
import com.danny.burge.wordsgame.logic.GameEvent
import com.danny.burge.wordsgame.logic.GameLogic
import com.danny.burge.wordsgame.ui.main.compose.GameUI
import com.danny.burge.wordsgame.ui.theme.WordsGameTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val gameLogic: GameLogic by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameLogic.onEvent(GameEvent.OnApplicationStarted)

        setContent {
            WordsGameTheme {
                GameUI(
                    onEventHandler = gameLogic::onEvent
                )
            }
        }
    }
}