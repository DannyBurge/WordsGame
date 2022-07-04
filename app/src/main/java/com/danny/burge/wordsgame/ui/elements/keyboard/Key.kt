package com.danny.burge.wordsgame.ui.elements.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danny.burge.wordsgame.ui.model.KeyboardKey
import com.danny.burge.wordsgame.ui.theme.LetterInWordColor
import com.danny.burge.wordsgame.ui.theme.LetterOnSpotColor
import com.danny.burge.wordsgame.ui.theme.shapeSmallCornerRadius

@Composable
fun Key(modifier: Modifier = Modifier, key: KeyboardKey, onClick: (String) -> Unit) {
    val boxColor = when {
        key.onSpot -> LetterOnSpotColor
        key.inWord -> LetterInWordColor
        key.notInWord -> Color.Transparent
        else -> Color.White
    }
    Box(
        modifier = modifier
            .heightIn(min = 38.dp)
            .padding(1.dp)
            .clickable(enabled = !key.notInWord, onClick = { onClick(key.label) })
            .background(boxColor, shapeSmallCornerRadius)
            .alpha(if (key.notInWord) 0F else 1F),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = key.label,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}