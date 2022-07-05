package com.danny.burge.wordsgame.ui.screens.main.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.*
import com.danny.burge.wordsgame.helpers.extention.advancedShadow
import com.danny.burge.wordsgame.helpers.utils.getBlankString
import com.danny.burge.wordsgame.ui.elements.ButtonWithImage
import com.danny.burge.wordsgame.ui.elements.ButtonWithText
import com.danny.burge.wordsgame.ui.elements.ShowEndGameDialog
import com.danny.burge.wordsgame.ui.elements.keyboard.WordsGameKeyboard
import com.danny.burge.wordsgame.ui.elements.letter.grid.LetterGrid
import com.danny.burge.wordsgame.ui.model.ColorMask
import com.danny.burge.wordsgame.ui.theme.shapeBigTopCornerRadius
import kotlinx.coroutines.launch

var cellIndex = 0
lateinit var isSnackBarNeeded: MutableState<Boolean>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenCompose(
    navigateToToSettings: NavigationFunc,
    checkWord: (String) -> Boolean,
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    // Check last answer if it is correct and show win dialog
    CheckIfWin(
        startNewGame = startNewGame,
        closeApp = closeApp
    )
    // Check attempt number if it is all of them and show lose dialog
    CheckIfLose(
        startNewGame = startNewGame,
        closeApp = closeApp
    )

    // for Keyboard
    val lettersOnSpot = remember { mutableStateListOf<String>() }
    val lettersInWord = remember { mutableStateListOf<String>() }
    val lettersNotInWord = remember { mutableStateListOf<String>() }

    val secretWordAnswerState = remember { getBlankString().toMutableStateList() }

    val snackBarHostState = remember { SnackbarHostState() }
    isSnackBarNeeded = remember { mutableStateOf(false) }

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

        Scaffold(modifier = Modifier
            .constrainAs(gamePlateRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topAppBarRef.bottom)
                bottom.linkTo(controlPanelRef.top)
                height = Dimension.fillToConstraints
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) { innerPadding ->
            LetterGrid(
                Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                onCellClick = {
                    cellIndex = it

                },
                secretWordAnswer = secretWordAnswerState
            )

            if (isSnackBarNeeded.value) {
                ShowSnackBar(snackBarHostState)
            }
        }

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
            isApplyEnable = secretWordAnswerState.all { it != EMPTY_LETTER },
            checkWord = {
                cellIndex = 0
                val checked = checkWord(secretWordAnswerState.joinToString("").lowercase())

                if (checked) {
                    with(WordsGameApp.state) {

                        val currentAnswer = answers[attempt.value]

                        currentAnswer.word.filterIndexed { index, _ ->
                            currentAnswer.colorMask[index] == ColorMask.LETTER_ON_SPOT
                        }.forEach { letter ->
                            if (letter.toString() !in lettersOnSpot) {
                                lettersOnSpot.add(letter.toString())
                            }
                        }

                        currentAnswer.word.filterIndexed { index, _ ->
                            currentAnswer.colorMask[index] == ColorMask.LETTER_IN_WORD
                        }.forEach { letter ->
                            if (letter.toString() !in lettersOnSpot &&
                                letter.toString() !in lettersInWord
                            ) {
                                lettersInWord.add(letter.toString())
                            }
                        }

                        if (!WordsGameApp.settings.keyboardVisibility) {
                            currentAnswer.word.filterIndexed { index, _ ->
                                currentAnswer.colorMask[index] == ColorMask.LETTER_NOT_IN_WORD
                            }.forEach { letter ->
                                if (letter.toString() !in lettersOnSpot &&
                                    letter.toString() !in lettersInWord &&
                                    letter.toString() !in lettersNotInWord
                                ) {
                                    lettersNotInWord.add(letter.toString())
                                }
                            }
                        }

                        attempt.value++
                    }
                    Log.d(DEBUG_LOG_TAG, "${secretWordAnswerState.toList()}")
                } else {
                    isSnackBarNeeded.value = true
                }

                secretWordAnswerState.clear()
                secretWordAnswerState.addAll(getBlankString().toMutableStateList())
            },
            changeLetter = { index, letter ->
                secretWordAnswerState[index] =
                    if (letter == BACKSPACE_LETTER) EMPTY_LETTER else letter
            }
        )
    }
}


@Composable
private fun CheckIfWin(
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    with(WordsGameApp.state) {
        if (attempt.value > 0) {
            if (answers.last().isCompletelyOpen) {
                showDialog.value = true
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
private fun CheckIfLose(
    startNewGame: () -> Unit,
    closeApp: () -> Unit
) {
    with(WordsGameApp) {
        if (state.attempt.value >= settings.attemptNumber || state.attempt.value == ATTEMPT_VALUE_SURRENDER) {
            state.showDialog.value = true
            ShowEndGameDialog(
                isVictory = false,
                secretWord = state.secretWord.value.word_letters,
                textAboutWord = state.secretWordDefinition.value,
                startNewGame = { startNewGame() },
                closeApp = { closeApp() })
        }
    }
}

@Composable
private fun ShowSnackBar(
    snackBarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val message = stringResource(id = R.string.wordNotInDictionaryMessage)
    val actionLabel = stringResource(id = R.string.wordNotInDictionaryActionLabel)
    LaunchedEffect(key1 = isSnackBarNeeded.value) {
        scope.launch {
            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
            isSnackBarNeeded.value = false
        }
    }

}

@Composable
private fun BottomControlPanel(
    modifier: Modifier,
    lettersOnSpot: List<String>,
    lettersInWord: List<String>,
    lettersNotInWord: List<String>,
    isApplyEnable: Boolean,
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
            checkWord = {
                checkWord()
            },
            isApplyEnable = isApplyEnable
        )
    }
}

@Composable
private fun BottomButtonRow(
    modifier: Modifier = Modifier,
    isApplyEnable: Boolean,
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
            checkWord = checkWord,
            enabled = isApplyEnable
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
private fun ApplyWordButton(modifier: Modifier, enabled: Boolean, checkWord: () -> Unit) {
    val focusManager = LocalFocusManager.current
    ButtonWithText(modifier, text = stringResource(id = R.string.applyWordButton), enabled,
        onClick = {
            focusManager.clearFocus()
            checkWord()
        }
    )
}

@Composable
private fun SurrenderButton(modifier: Modifier) {
    val focusManager = LocalFocusManager.current
    ButtonWithText(
        modifier = modifier,
        text = stringResource(id = R.string.surrenderButton),
        onClick = {
            focusManager.clearFocus()
            WordsGameApp.state.attempt.value = ATTEMPT_VALUE_SURRENDER
        }
    )
}