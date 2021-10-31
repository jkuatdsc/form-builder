package com.dsc.formbuilder

import android.util.Patterns
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

class FormField(val name: String, initial: String = "", val validators: List<Validators>) {

    var text: String by mutableStateOf(initial)
    var message: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    @Composable
    internal fun Content(
        enabled: Boolean = true,
        readOnly: Boolean = false,
        label: @Composable (() -> Unit)? = null,
        textStyle: TextStyle = LocalTextStyle.current,
        placeholder: @Composable (() -> Unit)? = null,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions(),
        singleLine: Boolean = false,
        maxLines: Int = Int.MAX_VALUE,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        shape: Shape =
            MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
        colors: TextFieldColors = TextFieldDefaults.textFieldColors()
    ){
        TextField(
            value = text,
            onValueChange = { text = it },
            label = label,
            enabled = enabled,
            textStyle = textStyle,
            readOnly = readOnly,
            modifier = Modifier.padding(10.dp),
            singleLine = singleLine,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = false,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )
    }

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun hideError(){
        message = ""
        hasError = false
    }

    fun clear() = "".also { text = it }

    internal fun validate(): Boolean {
        return validators.map {
            when (it){
                is Email -> {
                    if (!email()) showError(it.message)
                    email()
                }
                is Phone -> {
                    if (!phone()) showError(it.message)
                    phone()
                }
                is Required -> {
                    if (!required()) showError(it.message)
                    required()
                }
                is Max -> {
                    if (!max(it.limit)) showError(it.message)
                    max(it.limit)
                }
                is Min -> {
                    if (!min(it.limit)) showError(it.message)
                    min(it.limit)
                }
            }
        }.all { it }
    }


    private fun required(): Boolean = text.isNotEmpty()

    private fun max(limit: Double): Boolean = text.toDouble() < limit

    private fun min(limit: Double): Boolean = text.toDouble() > limit

    private fun email(): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    private val phoneRegex = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}\$")
    private fun phone(): Boolean = phoneRegex.containsMatchIn(text)
}