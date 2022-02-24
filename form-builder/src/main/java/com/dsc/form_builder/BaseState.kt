package com.dsc.form_builder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class BaseState<T>(
    val name: String,
    val transform: Transform<T>?,
    val validators: List<Validators>
) {

    abstract var value: T
    var errorMessage: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    fun showError(error: String) {
        hasError = true
        errorMessage = error
    }

    fun hideError() {
        errorMessage = ""
        hasError = false
    }

    open fun validate(): Boolean = true

    fun getData(): Any? {
        return if (transform == null) value else transform.transform(value)
    }

}