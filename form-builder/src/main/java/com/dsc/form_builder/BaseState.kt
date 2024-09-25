package com.dsc.form_builder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 *
 * This is the abstract base state that defines the basic behaviour for all form states.
 *
 * @param initial the initial value/state of the class.
 * @param name this is the name of the state. It is used to access the state when required in the form. It is also used when creating the class in the [getData] method of the [FormState] class.
 * @param transform this function is used to change the data type in the field state. You can use it to convert the data in the field to your preferred type e.g [String] to [Int]
 * @param validators this is the list of [Validators] that are used to validate the field state. By default most states will have an empty list. You can override this and provide your own list of validators.
 *
 *  @author [Linus Muema](https://github.com/linusmuema)
 *  @created 05/04/2022 - 10:00 AM
 */
abstract class BaseState<T>(
    val initial: T,
    val name: String,
    val transform: Transform<T>?,
    val validators: List<Validators>
) {

    /**
     * This is the state of the field. It can be anything specified by the extending class. For instance, for a [TextFieldState] it can be a [String] and for [SelectState] it can be a [List] of [String]s.
     */
    abstract var value: T
        internal set

    /**
     * This is the error message that is displayed when the field is invalid. You can access this property in order to display the error in the UI.
     */
    var errorMessage: String by mutableStateOf("")

    /**
     * This shows whether the state has an error or not. It is used to conditionally show the error on the UI. Once the state changes, this is set to [false].
     */
    var hasError: Boolean by mutableStateOf(false)


    /**
     * This function is used to update the state when an error is encountered. If a validation fails, this method is called with the error message and the error state is set. You can also use it if you're performing the validations manually.
     * @param error this is the error message that is passed from the validators.
     */
    fun showError(error: String) {
        hasError = true
        errorMessage = error
    }

    /**
     * This method is the opposite of [showError]. It is used to clear the error state. It sets the [errorMessage] to an empty string and [hasError] to [false]. On state update, this method is called to reset the field's unvalidated state.
     */
    fun hideError() {
        errorMessage = ""
        hasError = false
    }

    /**
     * This function is the basic validation method. It is called by the [FormState] class when the validation is requested. It basically runs all the validations from the specified [validators] list and if any of them fail, the [showError] is called to set the error state. Otherwise, it returns [true] to denote a valid form.
     */
    abstract fun validate(): Boolean

    /**
     * This function is used to get the data in the field. It is called by the [FormState] class when the form is submitted. It calls the [transform] function to convert the data to whatever type is specified.
     */
    open fun getData(): Any? {
        return if (transform == null) value else transform.transform(value)
    }

    /**
     * This function resets all form field values to their initial states.
     */
    fun reset() {
        this.value = initial
        this.hideError()
    }
}
