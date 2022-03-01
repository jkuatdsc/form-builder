package com.dsc.form_builder

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState(
    name: String,
    initial: String = "",
    transform: Transform<String>? = null,
    validators: List<Validators> = listOf(),
) : BaseState<String>(name, transform, validators) {

    override var value: String by mutableStateOf(initial)

    fun change(value: String) {
        hideError()
        this.value = value
    }


    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Email -> validateEmail(it.message)
                is Validators.Required -> validateRequired(it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                is Validators.MinChars -> validateMinChars(it.limit, it.message)
                is Validators.MaxChars -> validateMaxChars(it.limit, it.message)
                is Validators.MinValue -> validateMinValue(it.limit, it.message)
                is Validators.MaxValue -> validateMaxValue(it.limit, it.message)
                else -> throw Exception("Invalid validator. Did you mean Validators.Custom? $it")
            }
        }
        return validations.all { it }
    }

    private fun validateCustom(function: (String) -> Boolean, message: String): Boolean {
        val valid = function(value)
        if (!valid) showError(message)
        return valid
    }

    private fun validateEmail(message: String): Boolean {
        val valid = Patterns.EMAIL_ADDRESS.matcher(value).matches()
        if (!valid) showError(message)
        return valid
    }

    private fun validateRequired(message: String): Boolean {
        val valid = value.isNotEmpty()
        if (!valid) showError(message)
        return valid
    }

    private fun validateMaxChars(limit: Int, message: String): Boolean {
        val valid = value.length <= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMinChars(limit: Int, message: String): Boolean {
        val valid = value.length >= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMinValue(limit: Int, message: String): Boolean {
        val valid = value.isNumeric() && value.toDouble() >= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMaxValue(limit: Int, message: String): Boolean {
        val valid = value.isNumeric() && value.toDouble() <= limit
        if (!valid) showError(message)
        return valid
    }
}

