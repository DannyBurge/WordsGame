package com.danny.burge.wordsgame.ui.elements.keyboard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.danny.burge.wordsgame.ui.model.KeyboardKey

@Composable
fun WordsGameKeyboard(
    modifier: Modifier,
    lettersOnSpot: List<String>,
    lettersInWord: List<String>,
    lettersNotInWord: List<String>,
    onKeyClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier)
    {
        items(items = listOf(firstRow, secondRow, thirdRow)) { keyRow ->
            KeyRow(
                keyRow.toKeyboardRow(
                    lettersOnSpot = lettersOnSpot,
                    lettersInWord = lettersInWord,
                    lettersNotInWord = lettersNotInWord
                ),
                onKeyClick = onKeyClick
            )
        }
    }
}

private fun List<String>.toKeyboardRow(
    lettersOnSpot: List<String>,
    lettersInWord: List<String>,
    lettersNotInWord: List<String>
): List<KeyboardKey> {
    return this.map {
        KeyboardKey(
            label = it,
            inWord = it in lettersInWord,
            notInWord = it in lettersNotInWord,
            onSpot = it in lettersOnSpot
        )
    }
}

private val firstRow: List<String> = listOf(
    "й",
    "ц",
    "у",
    "к",
    "е",
    "н",
    "г",
    "ш",
    "щ",
    "з",
    "х"
)

private val secondRow: List<String> = listOf(
    "ф",
    "ы",
    "в",
    "а",
    "п",
    "р",
    "о",
    "л",
    "д",
    "ж",
    "э"
)

private val thirdRow: List<String> = listOf(
    "я",
    "ч",
    "с",
    "м",
    "и",
    "т",
    "ь",
    "б",
    "ю",
    "<"
)