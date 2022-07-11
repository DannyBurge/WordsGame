package com.danny.burge.wordsgame.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.danny.burge.wordsgame.database.models.Word
import com.danny.burge.wordsgame.helpers.utils.getBlankString
import com.danny.burge.wordsgame.ui.model.Answer

data class AppGameData(
    var secretWord: MutableState<Word> = mutableStateOf(Word()),
    var secretWordDefinition: MutableState<String> = mutableStateOf(""),

    var attempt: MutableState<Int> = mutableStateOf(0),

    var answers: SnapshotStateList<Answer> = mutableStateListOf(),
    var secretWordAnswer: SnapshotStateList<String> = mutableStateListOf(),
    var cellIndex: Int = 0,
    var secretWordAnswerApplied: Boolean = false,

    var showDialog: MutableState<Boolean> = mutableStateOf(false),

    // for Keyboard
    val lettersOnSpot: SnapshotStateList<String> = mutableStateListOf(),
    val lettersInWord: SnapshotStateList<String> = mutableStateListOf(),
    val lettersNotInWord: SnapshotStateList<String> = mutableStateListOf(),
) {
    fun clear() {
        secretWord.value = Word()
        secretWordDefinition.value = ""
        attempt.value = 0
        answers.clear()
        secretWordAnswer.clear()
        secretWordAnswer = getBlankString().toMutableStateList()
        cellIndex = WordsGameApp.settings.gameDifficulty
        showDialog.value = false

        lettersOnSpot.clear()
        lettersInWord.clear()
        lettersNotInWord.clear()
    }
}


