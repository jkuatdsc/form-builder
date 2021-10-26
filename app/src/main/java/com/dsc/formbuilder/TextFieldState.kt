package com.dsc.formbuilder

class TextFieldState {

    fun requiredValidator(input: String): Boolean {
        return input.isEmpty()
    }

    fun emailValidator(regexPattern: Regex, email: String): Boolean {
        return regexPattern.containsMatchIn(email)
    }

    fun phoneNumberValidator(regexPattern: Regex, phoneNumber: Long): Boolean {
        return regexPattern.containsMatchIn(phoneNumber.toString())
    }

    fun minValidator(limit: Double, input: String): Boolean {
        return input.toDouble() > limit
    }

    fun maxValidator(limit: Double, input: String): Boolean {
        return input.toDouble() < limit
    }
}