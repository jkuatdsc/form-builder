package com.dsc.formbuilder

data class Credentials(
    val email: String,
    val password: String,
    val age: Int,
    val gender: String,
    val happiness: Float,
    val hobbies: List<String>
)
