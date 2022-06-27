package com.danny.burge.wordsgame.ui.screens.main.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.ui.elements.ButtonWithText
import com.danny.burge.wordsgame.ui.elements.EndGameDialog
import com.danny.burge.wordsgame.ui.elements.LetterBox
import com.danny.burge.wordsgame.ui.model.Answer

typealias navigationFunc = () -> Unit

var secretWordAnswer: MutableList<String> = mutableListOf()
var attempt: Int = 0
private val showDialog = mutableStateOf(false)

@Composable
fun MainScreenCompose(
    liveDataAnswers: LiveData<MutableList<Answer>?>,
    liveDataSecretWord: LiveData<String?>,
    navigateToToSettings: navigationFunc,
    checkWord: (String, Int) -> Int,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (settingsButton, gamePlate, applyButton, surrenderButton) = createRefs()
        SettingsButton(
            Modifier
                .padding(4.dp)
                .wrapContentSize()
                .constrainAs(settingsButton) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            navigateToToSettings
        )
        GamePlate(
            Modifier
                .fillMaxWidth()
                .constrainAs(gamePlate) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(settingsButton.bottom)
                    bottom.linkTo(applyButton.top)
                },
            liveDataAnswers,
            liveDataSecretWord,
            startNewGame = { startNewGame() }
        ) { closeApp() }
        ApplyWordButton(
            Modifier
                .padding(4.dp)
                .wrapContentSize()
                .constrainAs(applyButton) {
                    start.linkTo(surrenderButton.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            checkWord
        )
        SurrenderButton(
            Modifier
                .padding(4.dp)
                .wrapContentSize()
                .constrainAs(surrenderButton) {
                    start.linkTo(parent.start)
                    end.linkTo(applyButton.start)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Composable
fun SettingsButton(modifier: Modifier, goToSettings: navigationFunc) {
    ButtonWithText(modifier, text = stringResource(id = R.string.settingsButton), goToSettings)
}

@Composable
fun ApplyWordButton(modifier: Modifier, checkWord: (String, Int) -> Int) {
    ButtonWithText(modifier, text = stringResource(id = R.string.applyWordButton))
    { attempt = checkWord(secretWordAnswer.joinToString(""), attempt) }
}

@Composable
fun SurrenderButton(modifier: Modifier) {
    ButtonWithText(
        modifier = modifier,
        text = stringResource(id = R.string.surrenderButton),
        onClick = { showDialog.value = true }
    )
}

@Composable
fun GamePlate(
    modifier: Modifier,
    liveDataAnswers: LiveData<MutableList<Answer>?>,
    liveDataSecretWord: LiveData<String?>,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    val answers = liveDataAnswers.observeAsState()
    val currentSecretWord = liveDataSecretWord.observeAsState()

    if (!currentSecretWord.value.isNullOrEmpty()) {

        // Check last answer if it is correct and show win dialog
        if (attempt > 0 && answers.value!!.last { it.word.isNotBlank() }.isCompletelyOpen) {
            ShowEndGameDialog(
                isVictory = true,
                secretWord = currentSecretWord.value!!,
                startNewGame = { startNewGame() },
                closeApp = { closeApp() })
        }
        // Check attempt number if it is all of them and show lose dialog
        if (attempt == WordsGameApp.gameDifficulty) {
            ShowEndGameDialog(
                isVictory = false,
                secretWord = currentSecretWord.value!!,
                startNewGame = { startNewGame() },
                closeApp = { closeApp() })
        }

        if (showDialog.value) {
            ShowEndGameDialog(
                isVictory = false,
                secretWord = currentSecretWord.value!!,
                startNewGame = {
                    showDialog.value = false
                    startNewGame()
                },
                closeApp = {
                    showDialog.value = false
                    closeApp()
                })

        }

        secretWordAnswer = currentSecretWord.value!!.map { "*" } as MutableList<String>

        val columnItems = (0 until WordsGameApp.attemptNumber).map { answers.value!![it] to it }
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(items = columnItems) { item ->
                LetterRow(
                    modifier = modifier,
                    rowLength = secretWordAnswer.size,
                    checkedWord = item.first,
                    isBlocked = item.second != attempt
                )
            }
        }
    }
}

@Composable
fun LetterRow(modifier: Modifier, rowLength: Int, checkedWord: Answer?, isBlocked: Boolean) {
    LazyRow(
        modifier = modifier
            .wrapContentSize()
            .padding(if (!isBlocked) 4.dp else 0.dp)
            .border(BorderStroke(1.dp, color = if (!isBlocked) Black else Color.Transparent))
            .padding(if (!isBlocked) 4.dp else 1.dp)
    ) {
        val rowItems = (0 until rowLength).map { index ->
            (checkedWord?.word?.get(index) ?: " ").toString() to index
        }
        items(items = rowItems) {
            val index = it.second
            val letter = (checkedWord?.word?.get(index) ?: " ").toString()
            val backgroundColorCode = checkedWord?.colorMask?.get(index) ?: 0

            LetterBox(letter, isBlocked, index, backgroundColorCode)
        }
    }
}


@Composable
fun ShowEndGameDialog(
    isVictory: Boolean,
    secretWord: String,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { /*TODO*/ },
            content = {
                EndGameDialog(
                    isVictory = isVictory,
                    secretWord = secretWord,
                    startNewGame = { startNewGame() },
                    closeDialog = { closeApp() })
            }
        )
    }
}