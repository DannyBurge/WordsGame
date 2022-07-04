package com.danny.burge.wordsgame.ui.elements.letter.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.ui.model.Answer
import com.danny.burge.wordsgame.ui.theme.BackgroundOnSelectedRow
import com.danny.burge.wordsgame.ui.theme.shapeMediumCornerRadius
import com.danny.burge.wordsgame.ui.theme.shapeSmallCornerRadius

@Composable
fun LetterRow(
    modifier: Modifier,
    rowLength: Int,
    checkedWord: Answer?,
    isBlocked: Boolean,
    currentAnswer: List<String>,
    onCellClick: (Int) -> Unit
) {
    LazyRow(
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
            (checkedWord?.word?.get(index) ?: " ").toString() to index
        }
        items(items = rowItems) {
            val index = it.second
            val letter: String
            val backgroundColorCode: Int
            if (isBlocked) {
                letter = (checkedWord?.word?.get(index) ?: " ").toString()
                backgroundColorCode = checkedWord?.colorMask?.get(index) ?: 0
            } else {
                letter = currentAnswer[index]
                backgroundColorCode = 0
            }
            LetterCell(letter, isBlocked, index, backgroundColorCode, onCellClick)
        }
    }
}