package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun checkBoxWithText(
    modifier: Modifier,
    text: String,
    defaultValue: Boolean
): Boolean {
    var checkBoxValue by remember { mutableStateOf(defaultValue) }

    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically),
            text = text,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Checkbox(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .wrapContentWidth(align = Alignment.End),
            checked = checkBoxValue,
            onCheckedChange = {
                checkBoxValue = it
            }
        )
    }
    return checkBoxValue
}