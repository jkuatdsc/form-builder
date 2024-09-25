package com.dsc.form_builder

/**
 *ChoiceState is a class that holds the chosen value from a selection of choices.
 * In this case the user is limited to a single choice.
 * Only [Validators.Required] and [Validators.Custom] are supported for this class.
 *
 * @param initial the initial value/state of the field. By default this is an empty string so none of the choices are selected.
 *
 * @param name the name of the state used to get an instance of the state from the form builder.
 * using the [FormBuilder.getState] method.
 *
 * @param transform the transformation function used to transform the  values of the state to a desired type.
 * This function will be applied to the value before it is returned.
 *
 * @param validators a list of [Validators] applied to the state's value.
 *
 * @author [Samwel Otieno](https://github.com/otienosamwel)
 */
class ChoiceState(
    name: String,
    initial: String = "",
    validators: List<Validators> = listOf(),
    transform: Transform<String>? = null,
) : TextFieldState(initial = initial, name = name, validators = validators, transform = transform) {

    /**
     * This function all [validators] passed in to th state class against the state's value.
     *
     * @throws Exception if the used [Validators] class is not supported.
     * @return true if all validators pass, false otherwise.
     */
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
