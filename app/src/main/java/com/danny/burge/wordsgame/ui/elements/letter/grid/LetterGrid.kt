package com.danny.burge.wordsgame.ui.elements.letter.grid

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danny.burge.wordsgame.app.WordsGameApp

@Composable
fun LetterGrid(
    modifier: Modifier,
    onCellClick: (Int) -> Unit,
    secretWordAnswer: List<String>
) {

    if (secretWordAnswer.isNotEmpty()) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(ScrollState(0))
        ) {
            val columnItems = (0 until WordsGameApp.settings.attemptNumber).map { it }
            columnItems.forEach {
                LetterRow(
                    modifier = modifier,
                    rowIndex = it,
                    rowLength = WordsGameApp.settings.gameDifficulty,
                    checkedWord = WordsGameApp.state.answers.getOrNull(it),
                    isBlocked = it != WordsGameApp.state.attempt.value,
                    onCellClick = onCellClick,
                    currentAnswer = secretWordAnswer
                )
            }
        }
    }
}