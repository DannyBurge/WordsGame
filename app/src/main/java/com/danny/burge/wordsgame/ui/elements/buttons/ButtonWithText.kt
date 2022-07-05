package com.danny.burge.wordsgame.ui.elements.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.ui.theme.shapeBigCornerRadius

@Composable
fun ButtonWithText(modifier: Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .alpha(if (enabled) 1F else 0.3f)
            .background(MaterialTheme.colorScheme.primary, shapeBigCornerRadius)
            .clip(shapeBigCornerRadius)
            .clickable(enabled = enabled) { onClick() }
            .wrapContentSize(),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}