package com.dsc.form_builder

class ChoiceState(
    name: String,
    validators: List<Validators>,
    transform: Transform<String>? = null,
) : TextFieldState(name = name, validators = validators, transform = transform){

    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Required -> validateRequired(it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                else -> throw Exception("${it::class.simpleName} validator cannot be applied to ChoiceState. Did you mean Validators.Custom?")
            }
        }
        return validations.all { it }
    }
}