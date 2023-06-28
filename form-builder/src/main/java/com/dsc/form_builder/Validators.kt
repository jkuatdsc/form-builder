package com.dsc.form_builder

import com.dsc.form_builder.format.DateFormat

private const val EMAIL_MESSAGE = "Invalid email address"
private const val REQUIRED_MESSAGE = "This field is required"
private const val PHONE_MESSAGE = "Invalid phone number"
private const val WEB_URL_MESSAGE = "Invalid web url"
private const val CARD_NUMBER_MESSAGE = "Invalid card number"
private const val DATE_MESSAGE = "Invalid date"

/**
 *
 * These are the types of validators available in the form builder library.
 * They all have the _message_ parameter to allow the developer to set their own custom error message.
 */
sealed interface Validators {

    /**
     * This validator is used to check for number of values in the state. It will return true if the values are more than the specified limit. In [TextFieldState] this is used to check for the minimum number of characters while in [SelectState], it is used to check for the minimum number of options selected.
     * @param limit the minimum value that the state should hold.
     * @param message the error message to display if the value is less than the limit.
     */
    class Min(var limit: Int, var message: String) : Validators

    /**
     * This validator is also used to check for values in the state. Unlike the [Min] validator, it will return true if the values are less than the specified limit.  In [TextFieldState] this is used to check for the maximum number of characters while in [SelectState], it is used to check for the maximum number of options selected.
     * @param limit the maximum value that the value can hold.
     * @param message the error message to display if the value is greater than the limit.
     */
    class Max(var limit: Int, var message: String) : Validators

    /**
     * This is an email validator. It will return true if the string value is a valid email address. The implementation makes use of the [android.util.Patterns] class to match the email address.
     * @param message the error message to display if the value is not a valid email address. By default we use the [EMAIL_MESSAGE] constant.
     */
    class Email(var message: String = EMAIL_MESSAGE) : Validators

    /**
     * This is a phone validator. It will return true if the string value is a valid phone number. The implementation makes use of the [android.util.Patterns] class to match the phone number.
     * @param message the error message to display if the value is not a valid phone number. By default we use the [PHONE_MESSAGE] constant.
     */
    class Phone(var message: String = PHONE_MESSAGE) : Validators

    /**
     * This is a web url validator. It will return true if the string value is a valid web url.
     * @param message the error message to display if the value is not a valid web url. By default we use the [WEB_URL_MESSAGE] constant.
     */
    class WebUrl(var message: String = WEB_URL_MESSAGE) : Validators

    /**
     * This is a card number validator. It will return true if the string value is a valid card number.
     * @param message the error message to display if the value is not a valid card number. By default we use the [CARD_NUMBER_MESSAGE] constant.
     */
    class CardNumber(var message: String = CARD_NUMBER_MESSAGE) : Validators

    /**
     * This is a date validator. It will return true if the string value is a valid date.
     * @param message the error message to display if the value is not a valid date. By default we use the [DATE_MESSAGE] constant.
     * @param format the pattern that specifies the expected format of the date string.
     */
    class Date(var message: String = DATE_MESSAGE, var format: DateFormat) : Validators

    /**
     * This validator is used to check for numeric values. It will return true is the value is numeric and is greater than or equal to the specified limit.
     * @param limit the minimum value that the value can hold.
     * @param message the error message to display if the value is not numeric or is less than the limit.
     */
    class MinValue(var limit: Int, var message: String) : Validators

    /**
     * This validator is also used to check for numeric values. It will return true is the value is numeric and is less than or equal to the specified limit.
     * @param limit the maximum value that the value can hold.
     * @param message the error message to display if the value is not numeric or is greater than the limit.
     */
    class MaxValue(var limit: Int, var message: String) : Validators

    /**
     * This is a required validator. It will return true if the value is not empty.
     * @param message the error message to display if the value is empty. By default we use the [REQUIRED_MESSAGE] constant.
     */
    class Required(var message: String = REQUIRED_MESSAGE) : Validators

    /**
     * This is validator gives you the option to provide your own implementation of the validator. You can pass in a custom function to validate the field value.
     *
     * Example: check if a string contains the word hello
     * ```kt
     * Validators.Custom(
     *     message = "value must have hello",
     *     function = { it.contains("hello") }
     * )
     * ```
     *
     *[Validators].[Custom] ([message] = "A custom message" , [function] = { customFunc(customParam) } )
     *
     *
     * @param function this is the lambda function that is called during validation. It provides the field value as a parameter and expects a [Boolean] return value.
     * @param message the error message to display if the validation fails.
     */
    class Custom(var message: String, var function: (value: Any) -> Boolean) : Validators
}
