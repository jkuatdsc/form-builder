package com.dsc.form_builder.format

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


/**
 *
 * These are the formatting interface for the [TextFieldState].
 * You can get the visual transformation to apply in your text input.
 *
 * @author [Linus Muema](https://github.com/linusmuema)
 */
interface Formatter {
    fun format(value: String): String
}


/**
 * Converts a [Formatter] to a [VisualTransformation] that can be applied to text input fields.
 * This transformation modifies how the text is visually presented based on the provided [Formatter].
 *
 * The function applies the [Formatter.format] method to the input text and returns a [TransformedText],
 * ensuring that the visual representation of the text differs from the raw input, while keeping the cursor
 * offset mapping consistent with the original text.
 *
 * @return A [VisualTransformation] that formats the input text based on the [Formatter].
 *
 * @author [Linus Muema](https://github.com/linusmuema)
 */
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
