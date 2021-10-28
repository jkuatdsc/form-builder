package com.dsc.formbuilder

private val EMAIL_REGEX = Regex("@*.\\.[a-zA-Z]+")
private val PHONE_NUMBER_REGEX = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}\$")

sealed class Validators

class Required(var message: String = "This field cannot be empty!") : Validators()

class Email(var regexPattern: Regex = EMAIL_REGEX, var message: String = "Please enter a valid email address") : Validators()

class PhoneNumber(var regexPattern: Regex = PHONE_NUMBER_REGEX, var message: String = "Please enter a valid phone number") : Validators()

class Min(var limit: Double, var message: String = "The minimum number of characters has not be reached") : Validators()

class Max(var limit: Double, var message: String = "Exceeded the maximum number of characters : $limit") : Validators()