package com.danny.burge.wordsgame.ui.elements

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.constants.DEBUG_LOG_TAG
import com.danny.burge.wordsgame.ui.screens.main.compose.secretWordAnswer
import com.danny.burge.wordsgame.ui.theme.*

@Composable
fun LetterBox(
    letter: String,
    isBlocked: Boolean,
    index: Int,
    backgroundColorCode: Int
) {
    var text by remember { mutableStateOf(letter) }

    var borderColor by remember { mutableStateOf(Color.Black) }

    val focusManager = LocalFocusManager.current
    val maxChar = 1
    val modifier = Modifier
        .onFocusChanged { borderColor = if (it.isFocused) Color.Red else Color.Black }
        .size(if (!isBlocked) 56.dp else 50.dp)
        .padding(PaddingValues(horizontal = 2.dp, vertical = 1.dp))
        .border(BorderStroke(0.5.dp, borderColor))
        .padding(1.dp)

    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it.take(maxChar)
            secretWordAnswer[index] = text
            if (text.length >= maxChar) {
                focusManager.moveFocus(FocusDirection.Right)
            }
            if (text.isEmpty()) {
                focusManager.moveFocus(FocusDirection.Left)
            }
            Log.d(DEBUG_LOG_TAG, "answer is -$secretWordAnswer-")
        },
        readOnly = isBlocked,
        decorationBox = { DecorationBox(text, isBlocked, backgroundColorCode) }
    )
}

@Composable
private fun DecorationBox(text: String, isBlocked: Boolean, backgroundColorCode: Int) {
    val color = when (backgroundColorCode) {
        1 -> if (isBlocked) LetterInWordColorBlocked else LetterInWordColor
        2 -> if (isBlocked) LetterOnSpotColorBlocked else LetterOnSpotColor
        else -> if (isBlocked) LetterColorBlocked else LetterColor
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = text,
            style = letterStyle,
            textAlign = TextAlign.Center,
        )
    }
}