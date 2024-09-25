package com.dsc.form_builder

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * This class represents the state of a switch form field.
 * It extends [BaseState] and manages the state and validations for switch inputs.
 *
 * @param name The name of the field used to access the state when required in the form.
 * @param initial The initial value/state of the switch. By default, it is false (off).
 * @param transform An optional function used to change the [Boolean] data type of the switch to a suitable type.
 * @param validators This is the list of [Validators] that are used to validate the switch state.
 *                   By default, the switch states will have an empty list. You can override this
 *                   and provide your own list of validators.
 *
 * @author [Nenoeldeeb]
 * @created [01/09/2024]
 */
class SwitchState(
	name: String,
	initial: Boolean = false,
	transform: Transform<Boolean>? = null,
	validators: List<Validators> = listOf(),
) : BaseState<Boolean>(
	initial = initial,
	name = name,
	transform = transform,
	validators = validators
) {
	
	/**
	 * The current value of the switch. It uses [mutableStateOf] to make it observable in Compose.
	 */
	override var value: Boolean by mutableStateOf(initial)
	
	/**
	 * Toggles the state of the switch between true (on) and false (off).
	 * This function also clears any previous error state by calling [hideError].
	 */
	fun toggle() {
		hideError()
		value = !value
	}
	
	/**
	 * Validates the switch state based on the provided validators.
	 * This function is called by [FormState] to confirm whether the switch field is valid.
	 *
	 * @return true if all validations pass, false otherwise.
	 */
	override fun validate(): Boolean {
		val validations = validators.map {
			when (it) {
				is Validators.Required -> validateRequired(it.message)
				is Validators.Custom -> validateCustom(
					it.function,
					it.message
				)
				
				else -> throw Exception("${it::class.simpleName} validator cannot be called on switch state. Did you mean Validators.Custom?")
			}
		}
		return validations.all { it }
	}
	
	/**
	 * Validates if the switch is in the required state (typically on/true).
	 *
	 * @param message The error message to display if the validation fails.
	 * @return true if the switch is in the required state, false otherwise.
	 */
	@VisibleForTesting
	internal fun validateRequired(message: String): Boolean {
		// For a switch, "required" typically means it must be turned on
		val valid = value
		if (!valid) showError(message)
		return valid
	}
	
	/**
	 * Allows for custom validation of the switch state.
	 *
	 * @param function A lambda that takes a Boolean and returns a Boolean.
	 *                 It should return true if the validation passes, false otherwise.
	 * @param message The error message to display if the validation fails.
	 * @return The result of the custom validation function.
	 */
	private fun validateCustom(
		function: (Boolean) -> Boolean,
		message: String
	): Boolean {
		val valid = function(value)
		if (!valid) showError(message)
		return valid
	}
}