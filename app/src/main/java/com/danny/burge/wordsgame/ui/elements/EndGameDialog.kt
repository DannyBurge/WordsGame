package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.ui.elements.buttons.ExitButton
import com.danny.burge.wordsgame.ui.elements.buttons.StartNewGameButton
import com.danny.burge.wordsgame.ui.theme.*

@Composable
fun ShowEndGameDialog(
    isVictory: Boolean,
    secretWord: String,
    textAboutWord: String?,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    if (WordsGameApp.state.showDialog.value) {
        Dialog(
            onDismissRequest = { },
            content = {
                EndGameDialog(
                    isVictory = isVictory,
                    secretWord = secretWord,
                    textAboutWord = textAboutWord,
                    startNewGame = { startNewGame() },
                    closeDialog = { closeApp() })
            }
        )
    }
}

@Composable
fun EndGameDialog(
    isVictory: Boolean,
    secretWord: String,
    textAboutWord: String?,
    startNewGame: () -> Unit,
    closeDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .heightIn(max = 400.dp)
            .background(
                MaterialTheme.colorScheme.background,
                shapeBigCornerRadius
            )
    ) {
        //Text body
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 60.dp)
                .heightIn(min = 50.dp, max = 200.dp)
                .verticalScroll(rememberScrollState()),
            text = if (textAboutWord.isNullOrEmpty()) {
                "$secretWord - определение недоступно"
            } else {
                textAboutWord
            },
            style = dialogBodyStyle,
            color = Color.White,
        )

        //Buttons
        ButtonRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(all = dimensionResource(id = R.dimen.paddingMedium)),
            startNewGame = startNewGame,
            closeDialog = closeDialog
        )
    }

    //Title
    Text(
        text = stringResource(id = if (isVictory) R.string.victoryTitle else R.string.loseTitle),
        style = dialogTitleStyle,
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
            .fillMaxWidth()
            .background(
                if (isVictory) VictoryBackgroundColor else LoseBackgroundColor,
                shapeBigCornerRadius
            )
            .padding(dimensionResource(id = R.dimen.paddingMedium))
    )
}

@Composable
private fun ButtonRow(
    modifier: Modifier,
    startNewGame: () -> Unit,
    closeDialog: () -> Unit
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        StartNewGameButton(
            modifier = Modifier
                .weight(1F)
                .padding(end = 8.dp),
            startNewGame = startNewGame
        )

        ExitButton(
            modifier = Modifier
                .weight(1F)
                .padding(start = 8.dp),
            closeApp = closeDialog
        )
    }
}

@Preview
@Composable
fun DialogPreview() {
    ShowEndGameDialog(
        isVictory = true,
        secretWord = "секретное слово",
        textAboutWord = null,
        startNewGame = {},
        closeApp = {}
    )
}