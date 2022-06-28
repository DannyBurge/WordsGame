package com.danny.burge.wordsgame.ui.elements.keyboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.ui.model.KeyboardKey

@Composable
fun KeyRow(keys: List<KeyboardKey>, onKeyClick: (String) -> Unit) {
    ConstraintLayout(
        Modifier.fillMaxWidth()
    ) {
        val lazyRow = createRef()
        LazyRow(
            Modifier
                .wrapContentSize()
                .constrainAs(lazyRow) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            items(items = keys) {
                Key(
                    modifier = Modifier,
                    key = it,
                    onClick = onKeyClick
                )
            }
        }
    }

}