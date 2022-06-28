package com.danny.burge.wordsgame.ui.elements.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danny.burge.wordsgame.ui.model.KeyboardKey
import com.danny.burge.wordsgame.ui.theme.LetterInWordColor
import com.danny.burge.wordsgame.ui.theme.LetterOnSpotColor

@Composable
fun Key(modifier: Modifier = Modifier, key: KeyboardKey, onClick: (String) -> Unit) {
    val shape = RoundedCornerShape(4.dp)
    Box(
        modifier = modifier
            .size(width = 28.dp, height = 43.dp)
            .padding(1.dp)
            .clip(shape)
            .clickable(enabled = key.notInWord != true, onClick = { onClick(key.label) })
            .background(
                when {
                    key.onSpot == true -> LetterOnSpotColor
                    key.inWord == true -> LetterInWordColor
                    key.notInWord == true -> Color.Transparent
                    else -> Color.White
                }
            )
            .padding(vertical = 8.dp, horizontal = 4.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = key.label,
            fontSize = 16.sp,
            color = if (key.notInWord == true) Color.Transparent else Color.Black
        )
    }
}