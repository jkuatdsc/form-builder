package com.dsc.form_builder

import androidx.compose.runtime.*

/**
 * SelectState is a state class that holds the selected values from a selection such as checkboxes.
 * In this case the user can make several selections and the state will hold the selected values.
 *
 * @param initial the initial value/state of the field. By default it is an empty list so no values are selected.
 *
 * @param name the name of the state used to get an instance of the state from the form builder.
 * using the [FormState.getState] method.
 *
 * @param transform the transformation function used to transform the values of the state to a desired type.
 * This function will be applied to the value before it is returned.
 *
 * @param validators a list of [Validators] applied to the state's value.
 *
 */
class SelectState(
    name: String,
    initial: MutableList<String> = mutableListOf(),
    transform: Transform<MutableList<String>>? = null,
    validators: List<Validators> = listOf()
) : BaseState<MutableList<String>>(initial = initial, name = name, transform = transform, validators) {

    /**
     * The variable that holds the selected values of the state.
     * This variable should only be updated via the [select] and [unselect] method.
     *
     */
    override var value: MutableList<String> = initial.toMutableStateList()

    /**
     * This function adds the selected item to the state. It also hides the error message.
     *
     * @param selectValue the selected value from multiple options.
     */
    fun select(selectValue: String) {
        value.add(selectValue)
        hideError()
    }

    /**
     * This function removes the selected item from the state.
     *
     * @param selectValue the selected value from multiple options.
     */
    fun unselect(selectValue: String) = value.remove(selectValue)

    /**
     * Runs all [validators] passed in to th state class against the state's value.
     *
     * @throws Exception if the used [Validators] class is not supported.
     * @return true if all validators pass, false otherwise.
     */
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

    /**
     *This function validates that the state's value is less than the [limit] passed in to the [Validators.Min] class.
     *
     * @param limit the limit that the state's value should exceed.
     * @param message the error message to be displayed if the state's value does not meet the validation criteria.
     * @return true if the state's value is more than the [limit] passed in to the [Validators.Min] class.
     */
    internal fun validateMin(limit: Int, message: String): Boolean {
        val valid = value.size >= limit
        if (!valid) showError(message)
        return valid
    }

    /**
     * This function validates that the state's value is less than the [limit] passed in to the [Validators.Max] class.
     *
     * @param limit the limit that the state's value should not exceed.
     * @param message the error message to be displayed if the state's value does not meet the validation criteria.
     * @return true if the state's value is less than the [limit] passed in to the [Validators.Max] class.
     */
    internal fun validateMax(limit: Int, message: String): Boolean {
        val valid = value.size <= limit
        if (!valid) showError(message)
        return valid
    }

    /**
     *This function validates that the state's value is not empty.
     * @param message the error message to be displayed if the state's value does not meet the validation criteria.
     * @return true if the state's value is not empty.
     */
    internal fun validateRequired(message: String): Boolean {
        val valid = value.isNotEmpty()
        if (!valid) showError(message)
        return valid
    }

    /**
     *This function validates that the state's value meets the criteria defined in the custom lambda
     * function passed in to the [Validators.Custom] class.
     *
     * @param function the custom lambda function that will be applied to the state's value.
     * @param message the error message to be displayed if the state's value does not meet the validation criteria.
     * @return true if the state's value meets the criteria defined in the custom lambda function passed in to the [Validators.Custom] class.
     */
    private fun validateCustom(function: (List<String>) -> Boolean, message: String): Boolean {
        val valid = function(value)
        if (!valid) showError(message)
        return valid
    }

    /**
     * This function gives the [FormState] a way to access the state's value.
     *
     *@return the state's value with all the transformations applied.
     */
    override fun getData(): Any? {
        return if (transform == null) value.toList() else transform.transform(value)
    }
}
