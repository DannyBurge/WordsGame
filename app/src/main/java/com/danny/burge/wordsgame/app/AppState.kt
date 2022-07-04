package com.danny.burge.wordsgame.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.danny.burge.wordsgame.database.models.Word
import com.danny.burge.wordsgame.ui.model.Answer

data class AppState(
    var secretWord: MutableState<Word> = mutableStateOf(Word()),
    var secretWordDefinition: MutableState<String> = mutableStateOf(""),

    var attempt: MutableState<Int> = mutableStateOf(0),

    var answers: SnapshotStateList<Answer> = mutableStateListOf(),

    var showDialog: MutableState<Boolean> = mutableStateOf(false),
) {
    fun clear() {
        secretWord.value = Word()
        secretWordDefinition.value = ""
        attempt.value = 0
        answers.clear()
        showDialog.value = false
    }
}


