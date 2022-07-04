package com.danny.burge.wordsgame.ui.screens.main.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.WordsGameApp
import com.danny.burge.wordsgame.constants.*
import com.danny.burge.wordsgame.extention.advancedShadow
import com.danny.burge.wordsgame.ui.elements.ButtonWithImage
import com.danny.burge.wordsgame.ui.elements.ButtonWithText
import com.danny.burge.wordsgame.ui.elements.keyboard.WordsGameKeyboard
import com.danny.burge.wordsgame.ui.elements.letter.grid.LetterGrid
import com.danny.burge.wordsgame.ui.theme.shapeBigTopCornerRadius
import com.danny.burge.wordsgame.utils.getBlankString

var cellIndex = 0

@Composable
fun MainScreenCompose(
    navigateToToSettings: NavigationFunc,
    checkWord: (String) -> Unit,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    // for Keyboard
    val lettersOnSpot = remember { mutableStateListOf<String>() }
    val lettersInWord = remember { mutableStateListOf<String>() }
    val lettersNotInWord = remember { mutableStateListOf<String>() }

    val secretWordAnswerState = remember { getBlankString().toMutableStateList() }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (
            topAppBarRef,
            gamePlateRef,
            controlPanelRef
        ) = createRefs()
        TopAppBar(
            modifier = Modifier
                .constrainAs(topAppBarRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                },
            navigateToToSettings = navigateToToSettings
        )

        LetterGrid(
            Modifier
                .fillMaxWidth()
                .constrainAs(gamePlateRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topAppBarRef.bottom)
                    bottom.linkTo(controlPanelRef.top)
                    height = Dimension.fillToConstraints
                },
            onCellClick = {
                cellIndex = it

            },
            startNewGame = {
                startNewGame()
                lettersOnSpot.clear()
                lettersInWord.clear()
                lettersNotInWord.clear()
            },
            closeApp = closeApp,
            secretWordAnswer = secretWordAnswerState
        )

        BottomControlPanel(
            modifier = Modifier
                .constrainAs(controlPanelRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .wrapContentHeight(),
            lettersOnSpot = lettersOnSpot,
            lettersInWord = lettersInWord,
            lettersNotInWord = lettersNotInWord,
            checkWord = {
                checkWord(secretWordAnswerState.joinToString("").lowercase())
                secretWordAnswerState.clear()
                secretWordAnswerState.addAll(getBlankString().toMutableStateList())

                with(WordsGameApp.state) {

                    val currentAnswer = answers[attempt.value]

                    currentAnswer.word.filterIndexed { index, _ ->
                        currentAnswer.colorMask[index] == LETTER_ON_SPOT_CODE
                    }.forEach { letter ->
                        if (letter.toString() !in lettersOnSpot) {
                            lettersOnSpot.add(letter.toString())
                        }
                    }

                    currentAnswer.word.filterIndexed { index, _ ->
                        currentAnswer.colorMask[index] == LETTER_IN_WORD_CODE
                    }.forEach { letter ->
                        if (letter.toString() !in lettersOnSpot &&
                            letter.toString() !in lettersInWord
                        ) {
                            lettersInWord.add(letter.toString())
                        }
                    }

                    currentAnswer.word.filterIndexed { index, _ ->
                        currentAnswer.colorMask[index] == LETTER_NOT_IN_WORD_CODE
                    }.forEach { letter ->
                        if (letter.toString() !in lettersOnSpot &&
                            letter.toString() !in lettersInWord &&
                            letter.toString() !in lettersNotInWord
                        ) {
                            lettersNotInWord.add(letter.toString())
                        }
                    }

                    attempt.value++
                }
                Log.d(DEBUG_LOG_TAG, "${secretWordAnswerState.toList()}")
            },
            changeLetter = { index, letter ->
                secretWordAnswerState[index] = letter
            }
        )
    }
}

@Composable
private fun BottomControlPanel(
    modifier: Modifier,
    lettersOnSpot: List<String>,
    lettersInWord: List<String>,
    lettersNotInWord: List<String>,
    checkWord: () -> Unit,
    changeLetter: (Int, String) -> Unit
) {
    Column(
        modifier = modifier
            .advancedShadow(
                cornersRadius = 16.dp,
                alpha = 0.5F,
                shadowBlurRadius = 8.dp,
                offsetY = (-1).dp,
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = shapeBigTopCornerRadius
            )
    ) {
        WordsGameKeyboard(
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            lettersOnSpot = lettersOnSpot,
            lettersInWord = lettersInWord,
            lettersNotInWord = lettersNotInWord,
            onKeyClick = {
                changeLetter(cellIndex, it)
            },
        )
        BottomButtonRow(
            modifier = Modifier.padding(16.dp),
            checkWord = checkWord
        )
    }
}

@Composable
private fun BottomButtonRow(
    modifier: Modifier = Modifier,
    checkWord: () -> Unit,
) {
    Row(modifier = modifier) {
        SurrenderButton(
            modifier = Modifier
                .weight(1F)
                .padding(end = 8.dp),
        )

        ApplyWordButton(
            modifier = Modifier
                .weight(1F)
                .padding(start = 8.dp),
            checkWord = checkWord
        )

    }
}

@Composable
private fun TopAppBar(modifier: Modifier, navigateToToSettings: NavigationFunc) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .advancedShadow(
                alpha = 0.5F,
                shadowBlurRadius = 8.dp,
                offsetY = 1.dp,
            )
            .background(color = MaterialTheme.colorScheme.onBackground)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .wrapContentWidth(Alignment.Start)
                .padding(16.dp),
            text = "Words Game".uppercase()
        )

        SettingsButton(
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
                .padding(8.dp),
            navigateToToSettings
        )
    }
}

@Composable
private fun SettingsButton(modifier: Modifier, goToSettings: NavigationFunc) {
    ButtonWithImage(modifier = modifier, onClick = goToSettings)
}

@Composable
private fun ApplyWordButton(modifier: Modifier, checkWord: () -> Unit) {
    ButtonWithText(modifier, text = stringResource(id = R.string.applyWordButton))
    {
        checkWord()
    }
}

@Composable
private fun SurrenderButton(modifier: Modifier) {
    ButtonWithText(
        modifier = modifier,
        text = stringResource(id = R.string.surrenderButton),
        onClick = { WordsGameApp.state.attempt.value = WordsGameApp.attemptNumber + 1 }
    )
}