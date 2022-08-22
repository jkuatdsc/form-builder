package com.dsc.formbuilder.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FormBuilderTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        shapes = Shapes,
        content = content,
        typography = Typography,
        colors = FormBuilderColors,
    )
}