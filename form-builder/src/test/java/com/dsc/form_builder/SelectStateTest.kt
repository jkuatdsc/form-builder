package com.dsc.form_builder

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

internal class SelectStateTest {

    @Nested
    inner class DescribingStateChanges {
        private val classToTest: SelectState = SelectState(name = "test")

        @Test
        fun `errors should be hidden`() {
            // Simulate an existing validation error
            classToTest.hasError = true
            classToTest.errorMessage = "error message"

            val newValue = "new item"      // Given a TextFieldState and a new value
            classToTest.select(newValue)    // When change is called

            assert(!classToTest.hasError)
            assert(classToTest.errorMessage.isEmpty())
        }

        @Test
        fun `state should be updated`() {
            val item = "new item"      // Given a TextFieldState and a new value
            classToTest.select(item)    // When change is called

            assert(classToTest.value.contains(item)) // Then state should have the item

            classToTest.unselect(item)
            assert(!classToTest.value.contains(item)) // Then state should NOT have the item
        }
    }

    @Nested
    inner class DescribingValidation {
        private val classToTest: SelectState = SelectState(name = "test")

        @Test
        fun `Validators_Required works correctly`(){
            // When state is empty
            val firstValidation = classToTest.validateRequired("should fail")
            assert(!firstValidation)

            classToTest.select("item")
            val secondValidation = classToTest.validateRequired("should pass")
            assert(secondValidation)
        }

        @ParameterizedTest
        @ArgumentsSource(MinArgumentsProvider::class)
        fun `Validators_Min works correctly`(value: MutableList<String>, min: Int, expected: Boolean){
            classToTest.value = value // set the field state

            val actual = classToTest.validateMin(min, "expected validation: $expected")
            assert(actual == expected)
        }

        @ParameterizedTest
        @ArgumentsSource(MaxArgumentsProvider::class)
        fun `Validators_Max works correctly`(value: MutableList<String>, max: Int, expected: Boolean){
            classToTest.value = value // set the field state

            val actual = classToTest.validateMax(max, "expected validation: $expected")
            assert(actual == expected)
        }
    }
}
