package com.danny.burge.wordsgame.ui.elements.letter.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.constants.EMPTY_LETTER
import com.danny.burge.wordsgame.ui.model.Answer
import com.danny.burge.wordsgame.ui.theme.BackgroundOnSelectedRow
import com.danny.burge.wordsgame.ui.theme.shapeMediumCornerRadius

@Composable
fun LetterRow(
    modifier: Modifier,
    rowLength: Int,
    checkedWord: Answer?,
    isBlocked: Boolean,
    rowIndex: Int,
    currentAnswer: List<String>,
    onCellClick: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = if (!isBlocked) 5.dp else 1.dp, horizontal = 0.dp)
            .wrapContentSize()
            .background(
                color = if (!isBlocked) BackgroundOnSelectedRow else Color.Transparent,
                shapeMediumCornerRadius
            )
            .padding(vertical = if (!isBlocked) 5.dp else 0.dp, horizontal = 3.dp)
    ) {
        val rowItems = (0 until rowLength).map { index ->
            (checkedWord?.word?.get(index) ?: EMPTY_LETTER).toString() to index
        }
        rowItems.forEach {
            val index = it.second
            val letter = if (isBlocked) {
                (checkedWord?.word?.get(index) ?: EMPTY_LETTER).toString()
            } else {
                currentAnswer[index]
            }
            LetterCell(
                letter = letter,
                isBlocked = isBlocked,
                index = index,
                rowIndex = rowIndex,
                backgroundColorCode = checkedWord?.colorMask?.get(index),
                onCellClick = onCellClick
            )
        }
    }
}