package com.dsc.form_builder

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SwitchStateTest {
	
	@Nested
	inner class DescribingStateChanges {
		
		private val classToTest: SwitchState = SwitchState(name = "test")
		
		@Test
		fun `errors should be hidden when toggled`() {
			// Simulate an existing validation error
			classToTest.hasError = true
			classToTest.errorMessage = "error message"
			
			classToTest.toggle()    // When toggle is called
			
			assert(!classToTest.hasError)
			assert(classToTest.errorMessage.isEmpty())
		}
		
		@Test
		fun `state should be updated when toggled`() {
			val initialState = classToTest.value
			classToTest.toggle()    // When toggle is called
			
			assert(classToTest.value != initialState)
			
			classToTest.toggle()    // Toggle again
			assert(classToTest.value == initialState)
		}
	}
	
	@Nested
	inner class DescribingValidation {
		
		private val classToTest: SwitchState = SwitchState(name = "test")
		
		@Test
		fun `Validators_Required works correctly`() {
			// When state is false (off)
			val firstValidation = classToTest.validateRequired("")
			assert(!firstValidation)
			
			classToTest.toggle() // Turn on the switch
			val secondValidation = classToTest.validateRequired("")
			assert(secondValidation)
		}
	}
}