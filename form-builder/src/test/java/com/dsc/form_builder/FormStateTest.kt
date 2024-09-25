package com.dsc.form_builder

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class FormStateTest {
	@Nested
	inner class DescribingFormState {
		
		private val formState = FormState(
			listOf(
				TextFieldState(name = "email"),
				SelectState(name = "hobbies"),
				ChoiceState(name = "gender"),
				TextFieldState(
					name = "age",
					initial = "34"
				),
				SwitchState(name = "active")
			)
		)
		
		private val emailState = formState.getState<TextFieldState>("email")
		private val hobbyState = formState.getState<SelectState>("hobbies")
		private val genderState = formState.getState<ChoiceState>("gender")
		private val ageState = formState.getState<TextFieldState>("age")
		private val statusState = formState.getState<SwitchState>("active")
		
		@Test
		fun `state should be reset to initial values`() {
			// Given a form state with values changed
			emailState.change("buider@gmail.com")
			hobbyState.select("Running")
			genderState.change("male")
			ageState.change("56")
			statusState.toggle()
			
			// When the form.reset is requested
			formState.reset()
			
			// Then all values are reset to the original state
			assert(emailState.value == "" && !emailState.hasError)
			assert(hobbyState.value == mutableListOf<String>() && !hobbyState.hasError)
			assert(genderState.value == "" && !genderState.hasError)
			assert(ageState.value == "34" && !ageState.hasError)
			assert(!statusState.value && !statusState.hasError)
		}
	}
}