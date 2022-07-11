package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danny.burge.wordsgame.helpers.extention.advancedShadow
import com.danny.burge.wordsgame.ui.theme.shapeBigCornerRadius

@Composable
fun SettingBox(
    modifier: Modifier = Modifier,
    settingsName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .advancedShadow(cornersRadius = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = shapeBigCornerRadius
            )
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = shapeBigCornerRadius
                )
                .fillMaxWidth()
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(vertical = 12.dp),
            text = settingsName,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Box(
            modifier = Modifier
        ) {
            content()
        }
    }
}