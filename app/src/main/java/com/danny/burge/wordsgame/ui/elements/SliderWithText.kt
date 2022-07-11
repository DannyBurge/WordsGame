package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun sliderWithTextValue(
    modifier: Modifier,
    range: ClosedFloatingPointRange<Float>,
    defaultValue: Int,
    onValueChanged: () -> Unit
): Float {
    var sliderPosition by remember { mutableStateOf(defaultValue.toFloat()) }
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = sliderPosition.toInt().toString(),
            textAlign = TextAlign.Center
        )
        Slider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            value = sliderPosition,
            valueRange = range,
            steps = (range.endInclusive - range.start -1F).toInt(),
            onValueChange = {
                onValueChanged()
                sliderPosition = it
            }
        )
    }
    return sliderPosition
}