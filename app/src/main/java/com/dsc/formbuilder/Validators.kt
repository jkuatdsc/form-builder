package com.dsc.formbuilder

sealed interface Validators
class Required(var message: String = "This field cannot be empty!") : Validators
class Email(var message: String = "Please enter a valid email address") : Validators
class PhoneNumber(var message: String = "Please enter a valid phone number") : Validators
class Min(var limit: Double, var message: String = "The minimum number of characters has not be reached") : Validators
class Max(var limit: Double, var message: String = "Exceeded the maximum number of characters : $limit") : Validators