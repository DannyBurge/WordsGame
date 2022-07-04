package com.danny.burge.wordsgame.ui.elements.letter.grid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.ui.elements.ShowEndGameDialog

@Composable
fun LetterGrid(
    modifier: Modifier,
    onCellClick: (Int) -> Unit,
    startNewGame: () -> Unit,
    closeApp: () -> Unit,
    secretWordAnswer: List<String>
) {
    // Check last answer if it is correct and show win dialog
    checkIfWin(startNewGame = startNewGame, closeApp = closeApp)
    // Check attempt number if it is all of them and show lose dialog
    checkIfLose(startNewGame = startNewGame, closeApp = closeApp)

    if (secretWordAnswer.isNotEmpty()) {

        LazyColumn(modifier = modifier.fillMaxWidth()) {
            val columnItems = (0 until WordsGameApp.attemptNumber).map { it }
            items(items = columnItems) { item ->
                LetterRow(
                    modifier = modifier,
                    rowLength = WordsGameApp.gameDifficulty,
                    checkedWord = WordsGameApp.state.answers.getOrNull(item),
                    isBlocked = item != WordsGameApp.state.attempt.value,
                    onCellClick = onCellClick,
                    currentAnswer = secretWordAnswer
                )
            }
        }
    }
}

@Composable
private fun checkIfWin(
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    with(WordsGameApp.state) {
        if (attempt.value > 0) {
            if (answers[attempt.value - 1].isCompletelyOpen) {
                ShowEndGameDialog(
                    isVictory = true,
                    secretWord = secretWord.value.word_letters,
                    textAboutWord = secretWordDefinition.value,
                    startNewGame = { startNewGame() },
                    closeApp = { closeApp() })
            }
        }
    }
}

@Composable
private fun checkIfLose(
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    with(WordsGameApp.state) {
        if (WordsGameApp.state.attempt.value >= WordsGameApp.attemptNumber) {
            ShowEndGameDialog(
                isVictory = false,
                secretWord = secretWord.value.word_letters,
                textAboutWord = secretWordDefinition.value,
                startNewGame = { startNewGame() },
                closeApp = { closeApp() })
        }
    }
}