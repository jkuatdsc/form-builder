package com.dsc.form_builder

import androidx.compose.runtime.*

/**
 * This class represents the state of a single form field.
 * It overrides members from [BaseState].
 * It manages validations for individual form fields inputs (field states) for example email, passwords, etc.
 * It also helps keep track of form field input changes and pass updates to the onValueChange callback.
 *
 * @param name The name of the field used to access the state when required in the form
 * @param initial The initial value/state of the field. By default it is an empty string.
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
) : BaseState<String>(initial = initial, name = name, transform = transform, validators = validators) {

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
    fun change(update: String) {
        hideError()
        this.value = update
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
                is Validators.Phone -> validatePhone(it.message)
                is Validators.WebUrl -> validateWebUrl(it.message)
                is Validators.CardNumber -> validateCardNumber(it.message)
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
    internal fun validateEmail(message: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"
        val valid = emailRegex.toRegex().matches(value)
        if (!valid) showError(message)
        return valid
    }


    /**
     *This function validates a Phone in [value]
     *It will return true if the string value is a valid phone number.
     *The implementation makes use of the [android.util.Patterns] class to match the phone number.
     *@param message the error message passed to [showError] to display if the value is not a valid phone number. By default we use the [PHONE_MESSAGE] constant.
     */
    internal fun validatePhone(message: String): Boolean {
        val phoneRegex = "(\\+[0-9]+)?(\\([0-9]+\\)[\\- ]*)?([0-9][0-9\\- ]+[0-9])"
        val valid = phoneRegex.toRegex().matches(value)
        if (!valid) showError(message)
        return valid
    }

    /**
     *This function validates a Web Url in [value]
     *It will return true if the string value is a valid web url.
     *@param message the error message passed to [showError] to display if the value is not a valid web url. By default we use the [WEB_URL_MESSAGE] constant.
     */
    internal fun validateWebUrl(message: String): Boolean {
        val webUrlRegex =
            "(https?://)?(www\\.)?[-a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_+~#?&/=]*)"
        val valid = webUrlRegex.toRegex().matches(value)
        if (!valid) showError(message)
        return valid
    }

    /**
     *This function validates a Card Number in [value]
     *It will return true if the string value is a valid card number.
     *@param message the error message passed to [showError] to display if the value is not a valid card number. By default we use the [CARD_NUMBER_MESSAGE] constant.
     */
    internal fun validateCardNumber(message: String): Boolean {
        val cardNumberRegex = "(^3[47][0-9]{13}\$)|" +
                "(^(6541|6556)[0-9]{12}\$)|" +
                "(^389[0-9]{11}\$)|" +
                "(^3(?:0[0-5]|[68][0-9])[0-9]{11}\$)|" +
                "(^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})\$\n)|" +
                "(^63[7-9][0-9]{13}\$)|" +
                "(^(?:2131|1800|35\\d{3})\\d{11}\$)|" +
                "(^9[0-9]{15}\$)|" +
                "(^(6304|6706|6709|6771)[0-9]{12,15}\$)|" +
                "(^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}\$)|" +
                "(^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))\$\n)|" +
                "(^(6334|6767)[0-9]{12}|(6334|6767)[0-9]{14}|(6334|6767)[0-9]{15}\$)|" +
                "(^(4903|4905|4911|4936|6333|6759)[0-9]{12}|(4903|4905|4911|4936|6333|6759)[0-9]{14}|(4903|4905|4911|4936|6333|6759)[0-9]{15}|564182[0-9]{10}|564182[0-9]{12}|564182[0-9]{13}|633110[0-9]{10}|633110[0-9]{12}|633110[0-9]{13}\$\n)|" +
                "(^(62[0-9]{14,17})\$)|" +
                "(^4[0-9]{12}(?:[0-9]{3})?\$)|" +
                "(^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})\$)"
        val valid = cardNumberRegex.toRegex().matches(value)
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
     * It will return true if the characters are lesser than or equal to the specified limit.
     * @param limit the maximum characters that [value] can hold.
     * @param message the error message passed to [showError] to display if the characters are greater than the limit.
     */
    internal fun validateMaxChars(limit: Int, message: String): Boolean {
        val valid = value.length <= limit
        if (!valid) showError(message)
        return valid
    }

    /**
     * A function that checks the lower limit i.e the least number of characters in [value].
     * It will return true if the characters are greater than or equal to the specified limit.
     * @param limit the least number of characters that [value] can hold.
     * @param message the error message passed to [showError] to display if the characters are lesser than the limit.
     */
    internal fun validateMinChars(limit: Int, message: String): Boolean {
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
    internal fun validateMinValue(limit: Int, message: String): Boolean {
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
    internal fun validateMaxValue(limit: Int, message: String): Boolean {
        val valid = value.isNumeric() && value.toDouble() <= limit
        if (!valid) showError(message)
        return valid
    }
}

