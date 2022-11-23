package com.dsc.formbuilder.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dsc.formbuilder.R

val interFontFamily = FontFamily(
    Font(R.font.light, FontWeight.Light),
    Font(R.font.medium, FontWeight.Medium),
    Font(R.font.regular, FontWeight.Normal),
)

val Typography = Typography(
    h6 = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Black,
    ),
    body1 = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
    ),
)