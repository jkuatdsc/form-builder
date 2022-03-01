package com.dsc.form_builder

class RadioGroupState(
    name: String,
    validators: List<Validators>,
    transform: Transform<String>? = null,
) : TextFieldState(name = name, validators = validators, transform = transform)