package com.danny.burge.wordsgame.helpers.extention

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0.5f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 8.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            left = 0f,
            top = 0f,
            right = this.size.width,
            bottom = this.size.height,
            radiusX = cornersRadius.toPx(),
            radiusY = cornersRadius.toPx(),
            paint = paint
        )
    }
}

fun Modifier.visible(
    isVisible: Boolean = true
) = this.alpha(if (isVisible) 1F else 0F)

fun Modifier.enable(
    isEnable: Boolean = true
) = this.alpha(if (isEnable) 1F else 0.3f)