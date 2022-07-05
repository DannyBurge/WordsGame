package com.danny.burge.wordsgame.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val shapeSmallCornerRadius = RoundedCornerShape(4.dp)
val shapeMediumCornerRadius = RoundedCornerShape(8.dp)
val shapeBigCornerRadius = RoundedCornerShape(16.dp)
val shapeBigTopCornerRadius = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

val letterStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    textAlign = TextAlign.Center,
    fontSize = 20.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.5.sp,
)

val dialogTitleStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 24.sp,
    letterSpacing = 1.sp,
    textAlign = TextAlign.Center,
)

val dialogBodyStyle = TextStyle(
    color = Color.Black,
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)