package com.danny.burge.wordsgame.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.danny.burge.wordsgame.R
import com.danny.burge.wordsgame.ui.theme.dialogTitleStyle

@Composable
fun EndGameDialog(
    isVictory: Boolean,
    secretWord: String,
    startNewGame: () -> Unit,
    closeDialog: () -> Unit
) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .wrapContentSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (titleRef, secretWordRef, newGameButtonRef, endGameButtonRef) = createRefs()
            Text(
                text = stringResource(id = if (isVictory) R.string.victoryTitle else R.string.loseTitle),
                style = dialogTitleStyle,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(titleRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    })
            Text(
                text = secretWord,
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(secretWordRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(titleRef.bottom)
                    })
            ButtonWithText(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(newGameButtonRef) {
                        start.linkTo(parent.start)
                        end.linkTo(endGameButtonRef.start)
                        top.linkTo(secretWordRef.bottom)
                        bottom.linkTo(parent.bottom)
                    },
                text = stringResource(id = R.string.startNewGame)
            ) {
                startNewGame()
            }
            ButtonWithText(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(endGameButtonRef) {
                        start.linkTo(newGameButtonRef.end)
                        end.linkTo(parent.end)
                        top.linkTo(secretWordRef.bottom)
                        bottom.linkTo(parent.bottom)
                    },
                text = stringResource(id = R.string.closeDialog)
            ) {
                closeDialog()
            }
        }
    }
}