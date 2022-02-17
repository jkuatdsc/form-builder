package com.dsc.form_builder

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState<T>(
    val name: String,
    initial: String = "",
    val transform: ((String) -> T)? = null,
    val validators: List<Validators> = listOf(),
) {

    var text: String by mutableStateOf(initial)
    var errorMessage: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    fun change(value: String) {
        hideError()
        text = value
    }

    fun showError(error: String) {
        hasError = true
        errorMessage = error
    }

    fun hideError() {
        errorMessage = ""
        hasError = false
    }

    fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Email -> validateEmail(it.message)
                is Validators.Required -> validateRequired(it.message)
                is Validators.MinChars -> validateMinChars(it.limit, it.message)
                is Validators.MaxChars -> validateMaxChars(it.limit, it.message)
                is Validators.MaxValue -> validateMaxValue(it.limit, it.message)
                is Validators.MinValue -> validateMinValue(it.limit, it.message)
            }
        }
        return validations.all { it }
    }

    private fun validateEmail(message: String): Boolean {
        val valid = Patterns.EMAIL_ADDRESS.matcher(text).matches()
        if (!valid) showError(message)
        return valid
    }

    private fun validateRequired(message: String): Boolean {
        val valid = text.isNotEmpty()
        if (!valid) showError(message)
        return valid
    }

    private fun validateMaxChars(limit: Int, message: String): Boolean {
        val valid = text.length <= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMinChars(limit: Int, message: String): Boolean {
        val valid = text.length >= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMinValue(limit: Int, message: String): Boolean {
        val valid = text.isNumeric() && text.toDouble() >= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMaxValue(limit: Int, message: String): Boolean {
        val valid = text.isNumeric() && text.toDouble() <= limit
        if (!valid) showError(message)
        return valid
    }
}

