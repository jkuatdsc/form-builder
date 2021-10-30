package com.dsc.formbuilder

private const val PHONE_MESSAGE = "invalid phone number"
private const val EMAIL_MESSAGE = "invalid email address"
private const val MIN_MESSAGE = "value cannot be less than"
private const val MAX_MESSAGE = "value cannot be more than"
private const val REQUIRED_MESSAGE = "this field is required"

sealed interface Validators
class Phone(var message: String = PHONE_MESSAGE) : Validators
class Email(var message: String = EMAIL_MESSAGE) : Validators
class Required(var message: String = REQUIRED_MESSAGE) : Validators
class Min(var limit: Double, var message: String = "$MIN_MESSAGE $limit") : Validators
class Max(var limit: Double, var message: String = "$MAX_MESSAGE $limit") : Validators