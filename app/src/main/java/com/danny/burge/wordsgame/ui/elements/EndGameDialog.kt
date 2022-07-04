package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.ui.theme.LoseBackgroundColor
import com.danny.burge.wordsgame.ui.theme.VictoryBackgroundColor
import com.danny.burge.wordsgame.ui.theme.dialogBodyStyle
import com.danny.burge.wordsgame.ui.theme.dialogTitleStyle

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
                if (isVictory) VictoryBackgroundColor else LoseBackgroundColor,
                RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            text = stringResource(id = if (isVictory) R.string.victoryTitle else R.string.loseTitle),
            style = dialogTitleStyle,
            modifier = Modifier
                .fillMaxWidth()
        )
        DialogBody(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                .wrapContentHeight(unbounded = false),
            textBody = if (textAboutWord.isNullOrEmpty()) {
                "$secretWord - определение недоступно"
            } else {
                textAboutWord
            },
            startNewGame = startNewGame,
            closeDialog = closeDialog
        )
    }
}

@Composable
private fun DialogBody(
    modifier: Modifier,
    textBody: String,
    startNewGame: () -> Unit,
    closeDialog: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    )
    {
        val (textBodyRef, buttonRowRef) = createRefs()
        Column(
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .constrainAs(textBodyRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonRowRef.top)
                }
                .fillMaxHeight()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = false),
                text = textBody,
                style = dialogBodyStyle,
                color = Color.White,
            )
        }
        ButtonRow(
            modifier = Modifier
                .constrainAs(buttonRowRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            startNewGame = startNewGame,
            closeDialog = closeDialog
        )
    }
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
        ButtonWithText(
            modifier = Modifier
                .wrapContentHeight()
                .weight(1F)
                .fillMaxWidth()
                .padding(end = 8.dp),
            text = stringResource(id = R.string.startNewGame)
        ) {
            startNewGame()
        }

        ButtonWithText(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(start = 8.dp),
            text = stringResource(id = R.string.closeDialog)
        ) {
            closeDialog()
        }
    }
}