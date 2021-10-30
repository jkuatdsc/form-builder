package com.dsc.formbuilder

class FormState {
    private var fields: List<FormField> = listOf()
    fun setFields(inputs: List<FormField>) = inputs.also { fields = it }

    fun validate(): Boolean {
        var valid = true
        for (field in fields) if (!field.validate()) {
            valid = false
            break
        }
        return valid
    }

    fun getData(): Map<String, String> = fields.map { it.name to it.text }.toMap()
}