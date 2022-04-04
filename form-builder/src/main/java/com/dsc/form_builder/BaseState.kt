package com.dsc.form_builder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * This is the abstract base state that defines the basic behaviour for all form states.
 *
 * @param name this is the name of the state. It is used to access the state when required in the form. It is also used when creating the class in the [getData] method of the [FormState] class.
 * @param transform this function is used to change the data type in the field state. You can use it to convert the data in the field to your preferred type e.g [String] to [Int]
 */
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

    abstract fun validate(): Boolean

    open fun getData(): Any? {
        return if (transform == null) value else transform.transform(value)
    }
}