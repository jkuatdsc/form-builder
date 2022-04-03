package com.dsc.form_builder

private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "this field is required"

sealed interface Validators {
    class Min(var limit: Int, var message: String) : Validators
    class Max(var limit: Int, var message: String) : Validators
    class Email(var message: String = EMAIL_MESSAGE) : Validators
    class MinValue(var limit: Int, var message: String) : Validators
    class MaxValue(var limit: Int, var message: String) : Validators
    class Required(var message: String = REQUIRED_MESSAGE) : Validators
    class Custom(var message: String, var function: (value: Any) -> Boolean) : Validators
}