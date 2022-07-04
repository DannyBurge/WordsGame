package com.danny.burge.wordsgame.ui.elements.letter.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.app.WordsGameApp
import com.danny.burge.wordsgame.ui.model.ColorMask
import com.danny.burge.wordsgame.ui.theme.*
import java.util.*

@Composable
fun LetterCell(
    letter: String,
    isBlocked: Boolean,
    index: Int,
    backgroundColorCode: ColorMask?,
    onCellClick: (Int) -> Unit,
) {
    var inFocus by remember { mutableStateOf(false) }

    val previousLetter = remember { mutableStateOf(letter) }
    val focusManager = LocalFocusManager.current

    val text = if (letter == "<") " " else letter

    val color = when (backgroundColorCode) {
        null -> if (isBlocked) LetterColorBlocked else LetterColor
        ColorMask.LETTER_NOT_IN_WORD -> LetterColorBlocked
        ColorMask.LETTER_IN_WORD -> LetterInWordColor
        ColorMask.LETTER_ON_SPOT -> LetterOnSpotColor
    }

    val focusRequester = remember { FocusRequester() }
    val modifier = Modifier
        .padding(
            PaddingValues(horizontal = if (!isBlocked) 2.dp else 1.dp, vertical = 0.dp)
        )
        .size(
            dimensionResource(id = if (!isBlocked) R.dimen.boxSizeSelected else R.dimen.boxSizeUnselected)
        )
        .clickable(enabled = !isBlocked) {
            focusRequester.requestFocus()
        }
        .focusRequester(focusRequester)
        .onFocusChanged {
            inFocus = it.isFocused
            if (inFocus) onCellClick(index)
        }
        .focusable()
    DecorationBox(modifier, color, text, inFocus)

//    if (text != previousLetter.value && inFocus) {
//        if (letter == "<") {
//            if (index == 0) {
//                focusManager.clearFocus()
//            } else {
//                focusManager.moveFocus(FocusDirection.Left)
//            }
//        } else {
//            if (index == WordsGameApp.settings.gameDifficulty - 1) {
//                focusManager.clearFocus()
//            } else {
//                focusManager.moveFocus(FocusDirection.Right)
//            }
//        }
//        previousLetter.value = text
//    }
}

@Composable
fun DecorationBox(modifier: Modifier, color: Color, text: String, inFocus: Boolean) {
    ConstraintLayout(
        modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(color, shapeSmallCornerRadius)
    ) {
        val (focusMarker, textBox) = createRefs()
        Text(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(textBox) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = text.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            style = letterStyle,
            textAlign = TextAlign.Center,
        )
        Box(modifier = Modifier
            .background(
                if (inFocus) LetterColorInFocus else Color.Transparent,
                shapeSmallCornerRadius
            )
            .fillMaxSize()
            .constrainAs(focusMarker) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
    }
}