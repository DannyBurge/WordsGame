package com.danny.burge.wordsgame.ui.elements

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

@Composable
fun LetterRow(modifier: Modifier, rowLength: Int, checkedWord: Answer?, isBlocked: Boolean) {
    val shape = RoundedCornerShape(8.dp)

    LazyRow(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = if (!isBlocked) BackgroundOnSelectedRow else Color.Transparent,
                shape
            )
            .padding(horizontal = 3.dp)
            .padding(vertical = if (!isBlocked) 4.dp else 1.dp)
    ) {
        val rowItems = (0 until rowLength).map { index ->
            (checkedWord?.word?.get(index) ?: " ").toString() to index
        }
        items(items = rowItems) {
            val index = it.second
            val letter = (checkedWord?.word?.get(index) ?: " ").toString()
            val backgroundColorCode = checkedWord?.colorMask?.get(index) ?: 0

            LetterBox(letter, isBlocked, index, backgroundColorCode)
        }
    }
}