package com.dsc.form_builder

import android.util.Patterns
import androidx.compose.runtime.*

/**
 * This class represents the state of a single form field.
 * It overrides members from [BaseState].
 * It manages validations for individual form fields inputs (field states) for example email, passwords, etc.
 * It also helps keep track of form field input changes and pass updates to the onValueChange callback.
 *
 * @param name The name of the field used to access the state when required in the form
 * @param initial The initial value which is of type [String] on the [value]
 * @param transform The function used to change the [String] data type on the text field to a suitable type e.g [String] to [Int].
 * @param validators This is the list of [Validators] that are used to validate the field state. By default the field states will have an empty list. You can override this and provide your own list of validators.
 *
 * @author [Joy Kangangi](https://github.com/joykangangi)
 * @created 06/04/2022 - 2:50 p.m.
 *
 */
open class TextFieldState(
    name: String,
    initial: String = "",
    transform: Transform<String>? = null,
    validators: List<Validators> = listOf(),
) : BaseState<String>(name, transform, validators) {

    /**
     * A mutable value holder that reads to the initial parameter during the execution of a [Composable]
     * function, the current [RecomposeScope] will be subscribed to changes of this value.
     */
    override var value: String by mutableStateOf(initial)

    /**
     * A function to update the [RecomposeScope] with the latest [value].
     * Once the [value] changes it clears errors by the help of
     * [BaseState.hideError]
     */
    fun change(value: String) {
        hideError()
        this.value = value
    }

    /**
     *This function is used to validate all text field inputs by checking against
     *the corresponding validator from the list of [validators].
     * The validation checks are functions to validate the field values.
     * and returns true only if all fields are valid.
     * It is
     * It is used in [FormState] to confirm whether all fields in the form are valid.
     */

    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Email -> validateEmail(it.message)
                is Validators.Required -> validateRequired(it.message)
                is Validators.Min -> validateMinChars(it.limit, it.message)
                is Validators.Max -> validateMaxChars(it.limit, it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                is Validators.MinValue -> validateMinValue(it.limit, it.message)
                is Validators.MaxValue -> validateMaxValue(it.limit, it.message)
            }
        }
        return validations.all { it }
    }

    /**
     * This function allows customization of validations absent in the library to validate the field [value]
     * @param function the lambda function that is called during validation.
     * It takes the field value which is a [String] as a parameter and expects a [Boolean] return value.
     * @param message the error message to display if the validation fails.
     */
    internal fun validateCustom(function: (String) -> Boolean, message: String): Boolean {
        val valid = function(value)
        if (!valid) showError(message)
        return valid
    }

    /**
     *This function validates an Email in [value]
     *It will return true if the string value is a valid email address.
     *The implementation makes use of the [android.util.Patterns] class to match the email address.
     *@param message the error message passed to [showError] to display if the value is not a valid email address. By default we use the [EMAIL_MESSAGE] constant.
     */
    private fun validateEmail(message: String): Boolean {
        val valid = Patterns.EMAIL_ADDRESS.matcher(value).matches()
        if (!valid) showError(message)
        return valid
    }

    /**
     * This function validates a required field.
     * It will return true if the value is not empty.
     * @param message the error message passed to [showError] to display if the [value] is empty. By default we use the [REQUIRED_MESSAGE] constant.
     */
    internal fun validateRequired(message: String): Boolean {
        val valid = value.isNotEmpty()
        if (!valid) showError(message)
        return valid
    }

    /**
     * A function that checks the upper limit i.e. maximum number of characters in [value].
     * It will return true if the characters are lesser than the specified limit.
     * @param limit the maximum characters that [value] can hold.
     * @param message the error message passed to [showError] to display if the characters are greater than the limit.
     */
    private fun validateMaxChars(limit: Int, message: String): Boolean {
        val valid = value.length <= limit
        if (!valid) showError(message)
        return valid
    }

    /**
     * A function that checks the lower limit i.e the least number of characters in [value].
     * It will return true if the characters are greater than the specified limit.
     * @param limit the least number of characters that [value] can hold.
     * @param message the error message passed to [showError] to display if the characters are lesser than the limit.
     */
    private fun validateMinChars(limit: Int, message: String): Boolean {
        val valid = value.length >= limit
        if (!valid) showError(message)
        return valid
    }


    /**
     * A function that checks the lower limit i.e the least numeric value in [value] when transformed to [Int] by [transform].
     * It will return true if the numeric value is greater than or equal to the specified limit.
     * @param limit the least numeric value that [value] can hold.
     * @param message the error message passed to [showError] to display if the numeric value is lesser than the limit.
     */
    private fun validateMinValue(limit: Int, message: String): Boolean {
        val valid = value.isNumeric() && value.toDouble() >= limit
        if (!valid) showError(message)
        return valid
    }

    /**
     * A function that checks the upper limit i.e. maximum numeric value in [value] when transformed to [Int] by [transform].
     * It will return true if the numeric value is lesser than or equal to the specified limit.
     * @param limit the greatest numeric value that [value] can hold.
     * @param message the error message passed to [showError] to display if the numeric value is greater than the limit.
     */
    private fun validateMaxValue(limit: Int, message: String): Boolean {
        val valid = value.isNumeric() && value.toDouble() <= limit
        if (!valid) showError(message)
        return valid
    }
}

