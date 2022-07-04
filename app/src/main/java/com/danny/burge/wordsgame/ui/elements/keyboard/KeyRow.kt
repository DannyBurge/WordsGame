package com.danny.burge.wordsgame.ui.elements.keyboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.ui.model.KeyboardKey

@Composable
fun KeyRow(modifier: Modifier = Modifier, keys: List<KeyboardKey>, onKeyClick: (String) -> Unit) {
    ConstraintLayout(
        modifier.fillMaxWidth()
    ) {
        val lazyRow = createRef()
        Row(
            Modifier
                .wrapContentSize()
                .constrainAs(lazyRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            keys.forEach {
                Key(
                    modifier = Modifier.weight(1F),
                    key = it,
                    onClick = onKeyClick
                )
            }
        }
    }

}