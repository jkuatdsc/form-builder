package com.dsc.formbuilder

import android.util.Patterns
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormField(val name: String, initial: String = "", val validators: List<Validators>) {

    var text: String by mutableStateOf(initial)
    var message: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    @Composable
    internal fun Content(){
        TextField(value = text, onValueChange = { text = it })
    }

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun hideError(){
        message = ""
        hasError = false
    }

    fun clear() = "".also { text = it }

    internal fun validate(): Boolean {
        return validators.map {
            when (it){
                is Email -> {
                    if (!email()) showError(it.message)
                    email()
                }
                is Phone -> {
                    if (!phone()) showError(it.message)
                    phone()
                }
                is Required -> {
                    if (!required()) showError(it.message)
                    required()
                }
                is Max -> {
                    if (!max(it.limit)) showError(it.message)
                    max(it.limit)
                }
                is Min -> {
                    if (!min(it.limit)) showError(it.message)
                    min(it.limit)
                }
            }
        }.all { it }
    }


    private fun required(): Boolean = text.isNotEmpty()

    private fun max(limit: Double): Boolean = text.toDouble() < limit

    private fun min(limit: Double): Boolean = text.toDouble() > limit

    private fun email(): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    private val phoneRegex = Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}\$")
    private fun phone(): Boolean = phoneRegex.containsMatchIn(text)
}