package com.dsc.form_builder.format

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


/**
 *
 * These are the formatting interface for the [TextFieldState].
 * You can get the visual transformation to apply in your text input.
 */
interface Formatter {
    fun format(value: String): String
}

internal fun Formatter.toVisualTransformation(): VisualTransformation {
    return VisualTransformation {
        val output = format(it.text)
        TransformedText(
            AnnotatedString(output),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = output.length
                override fun transformedToOriginal(offset: Int): Int = it.text.length
            }
        )
    }
}
