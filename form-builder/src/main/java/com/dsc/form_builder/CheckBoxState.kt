package com.dsc.form_builder

import androidx.compose.runtime.mutableStateListOf

class CheckBoxState(
    name: String,
    transform: Transform<MutableList<String>>? = null,
    validators: List<Validators> = listOf()
) : BaseState<MutableList<String>>(name = name, transform = transform, validators) {

    override var value: MutableList<String> = mutableStateListOf()

    fun check(checkValue: String) {
        value.add(checkValue)
        hideError()
    }

    fun uncheck(uncheckValue: String) = value.remove(uncheckValue)

    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Required -> validateRequired(it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                else -> throw Exception("${it::class.simpleName} validator cannot be called on checkbox state. Did you mean Validators.Custom?")
            }
        }
        return validations.all { it }
    }

    private fun validateRequired(message: String): Boolean {
        val valid = value.isNotEmpty()
        if (!valid) showError(message)
        return valid
    }

    private fun validateCustom(function: (List<String>) -> Boolean, message: String): Boolean {
        val valid = function(value)
        if (!valid) showError(message)
        return valid
    }

    override fun getData(): Any? {
        return if (transform == null) value.toList() else transform.transform(value)
    }

}