package com.danny.burge.wordsgame.ui.screens.main.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.NavigationCommand
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.constants.ATTEMPT_VALUE_SURRENDER
import com.danny.burge.wordsgame.constants.BACKSPACE_LETTER
import com.danny.burge.wordsgame.constants.EMPTY_LETTER
import com.danny.burge.wordsgame.helpers.extention.advancedShadow
import com.danny.burge.wordsgame.helpers.utils.getBlankString
import com.danny.burge.wordsgame.logic.GameEvent
import com.danny.burge.wordsgame.ui.elements.ShowEndGameDialog
import com.danny.burge.wordsgame.ui.elements.TopAppBar
import com.danny.burge.wordsgame.ui.elements.buttons.ApplyWordButton
import com.danny.burge.wordsgame.ui.elements.buttons.SurrenderButton
import com.danny.burge.wordsgame.ui.elements.keyboard.WordsGameKeyboard
import com.danny.burge.wordsgame.ui.elements.letter.grid.LetterGrid
import com.danny.burge.wordsgame.ui.model.ColorMask
import com.danny.burge.wordsgame.ui.theme.shapeBigTopCornerRadius
import kotlinx.coroutines.launch

lateinit var isSnackBarNeeded: MutableState<Boolean>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenCompose(
    onEventHandler: (GameEvent) -> Unit,
    onNavigationCommand: (NavigationCommand) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    CheckIfGameEnded(
        startNewGame = { onEventHandler(GameEvent.OnNewGameClicked) },
        closeApp = { onEventHandler(GameEvent.OnApplicationClosed) }
    )

    val snackBarHostState = remember { SnackbarHostState() }
    isSnackBarNeeded = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            isMainScreen = true,
            onNavigationCommand = onNavigationCommand
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
                    onEventHandler(GameEvent.OnTileFocused(it))
                },
                secretWordAnswer = WordsGameApp.state.secretWordAnswer
            )

            if (isSnackBarNeeded.value) {
                ShowSnackBar(
                    snackBarHostState = snackBarHostState,
                    onEventHandler = onEventHandler
                )
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
            lettersOnSpot = WordsGameApp.state.lettersOnSpot,
            lettersInWord = WordsGameApp.state.lettersInWord,
            lettersNotInWord = WordsGameApp.state.lettersNotInWord,
            isApplyEnable = WordsGameApp.state.secretWordAnswer.all { it != EMPTY_LETTER },
            checkWord = {
                with(WordsGameApp.state) {
                    cellIndex = WordsGameApp.settings.gameDifficulty

                    onEventHandler(GameEvent.OnWordApplied)

                    if (secretWordAnswerApplied) {
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

                        if (WordsGameApp.settings.hideKeysInKeyboard) {
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
                        WordsGameApp.state.secretWordAnswer.clear()
                        WordsGameApp.state.secretWordAnswer.addAll(getBlankString().toMutableStateList())

                    } else {
                        isSnackBarNeeded.value = true
                    }
                }

            },
            changeLetter = { letter ->
                if (WordsGameApp.state.cellIndex in 0 until WordsGameApp.settings.gameDifficulty) {
                    onEventHandler(
                        GameEvent.OnInput(
                            index = WordsGameApp.state.cellIndex,
                            input = if (letter == BACKSPACE_LETTER) EMPTY_LETTER else letter
                        )
                    )

                    if (letter == BACKSPACE_LETTER) {
                        if (WordsGameApp.state.cellIndex != 0) {
                            focusManager.moveFocus(FocusDirection.Left)
                        }
                    } else {
                        if (WordsGameApp.state.cellIndex != WordsGameApp.settings.gameDifficulty - 1) {
                            focusManager.moveFocus(FocusDirection.Right)
                        }
                    }
                }
            }
        )
    }
}


@Composable
private fun CheckIfGameEnded(
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

                return
            }
        }

        if (attempt.value >= WordsGameApp.settings.attemptNumber || attempt.value == ATTEMPT_VALUE_SURRENDER) {
            showDialog.value = true
            ShowEndGameDialog(
                isVictory = false,
                secretWord = secretWord.value.word_letters,
                textAboutWord = secretWordDefinition.value,
                startNewGame = { startNewGame() },
                closeApp = { closeApp() })
        }
    }
}

@Composable
private fun ShowSnackBar(
    snackBarHostState: SnackbarHostState,
    onEventHandler: (GameEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val message = stringResource(id = R.string.wordNotInDictionaryMessage)
    val actionLabel = stringResource(id = R.string.wordNotInDictionaryActionLabel)
    LaunchedEffect(key1 = isSnackBarNeeded.value) {
        scope.launch {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
            when (snackBarResult) {
                SnackbarResult.Dismissed -> {}
                SnackbarResult.ActionPerformed -> onEventHandler(
                    GameEvent.OnSnackBarAddPressed
                )
            }
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
    changeLetter: (String) -> Unit
) {
    Column(
        modifier = modifier
            .advancedShadow(cornersRadius = 16.dp)
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
                changeLetter(it)
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
    val focusManager = LocalFocusManager.current
    Row(modifier = modifier) {
        SurrenderButton(
            modifier = Modifier
                .weight(1F)
                .padding(end = 8.dp),
            onClick = {
                focusManager.clearFocus()
                WordsGameApp.state.attempt.value = ATTEMPT_VALUE_SURRENDER
            }
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