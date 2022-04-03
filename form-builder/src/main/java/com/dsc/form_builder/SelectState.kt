package com.dsc.form_builder

import androidx.compose.runtime.mutableStateListOf

class SelectState(
    name: String,
    transform: Transform<MutableList<String>>? = null,
    validators: List<Validators> = listOf()
) : BaseState<MutableList<String>>(name = name, transform = transform, validators) {

    override var value: MutableList<String> = mutableStateListOf()

    fun select(selectValue: String) {
        value.add(selectValue)
        hideError()
    }

    fun unselect(selectValue: String) = value.remove(selectValue)

    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Min -> validateMin(it.limit, it.message)
                is Validators.Max -> validateMax(it.limit, it.message)
                is Validators.Required -> validateRequired(it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                else -> throw Exception("${it::class.simpleName} validator cannot be called on checkbox state. Did you mean Validators.Custom?")
            }
        }
        return validations.all { it }
    }

    private fun validateMin(limit: Int, message: String): Boolean {
        val valid = value.size >= limit
        if (!valid) showError(message)
        return valid
    }

    private fun validateMax(limit: Int, message: String): Boolean {
        val valid = value.size <= limit
        if (!valid) showError(message)
        return valid
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