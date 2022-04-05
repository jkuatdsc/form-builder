package com.dsc.form_builder

private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "this field is required"

/**
 * These are the types of validators available in the form builder library.
 * They all have the [message] parameter to allow the developer to set their own custom error message.
 */
sealed interface Validators {

    /**
     * This validator is used to check for number of values in the state. It will return true if the values are less than the specified limit. In [TextFieldState] this is used to check for the minimum number of characters while in [SelectState], it is used to check for the minimum number of options selected.
     * @param limit the maximum value that the value can hold.
     * @param message the error message to display if the value is not less than the limit.
     */
    class Min(var limit: Int, var message: String) : Validators

    /**
     * This validator is also used to check for values in the state. Unlike the [Min] validator, it will return true if the values are greater than the specified limit.  In [TextFieldState] this is used to check for the maximum number of characters while in [SelectState], it is used to check for the maximum number of options selected.
     * @param limit the minimum value that the value can hold.
     * @param message the error message to display if the value is not greater than the limit.
     */
    class Max(var limit: Int, var message: String) : Validators

    /**
     * This is an email validator. It will return true if the string value is a valid email address. The implementation makes use of the [android.util.Patterns] class to match the email address.
     * @param message the error message to display if the value is not a valid email address. By default we use the [EMAIL_MESSAGE] constant.
     */
    class Email(var message: String = EMAIL_MESSAGE) : Validators

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
     * @param function this is the lambda function that is called during validation. It provides the field value as a parameter and expects a [Boolean] return value.
     * @param message the error message to display if the validation fails.
     */
    class Custom(var message: String, var function: (value: Any) -> Boolean) : Validators
}