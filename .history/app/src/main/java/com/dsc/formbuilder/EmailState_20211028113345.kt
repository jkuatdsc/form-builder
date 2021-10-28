package com.dsc.formbuilder

private const val EMAIL_VALIDATION_REGEX = "$(.+)@(.+)\$"
class EmailState: TextField(
validator = {email -> isEmailValid(valid)},
errorMessage =::errorMessage,){
}
private fun isEmailValid(email: String ): Boolean{
return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}
private fun errorMessage(email: String) = "Email $email is invalid"