package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.danny.burge.wordsgame.R

@Composable
fun ButtonWithImage(modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .wrapContentSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_settings_black_24dp),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}