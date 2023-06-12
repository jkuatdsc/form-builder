package com.dsc.form_builder

import com.dsc.form_builder.format.CardFormatter
import com.dsc.form_builder.format.DateFormat
import com.dsc.form_builder.format.DateFormatter
import com.dsc.form_builder.format.Formatter
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

internal class FormatterTest {

    @Nested
    inner class DescribingFormatting {

        @ParameterizedTest
        @ArgumentsSource(CreditCardFormatterProvider::class)
        fun `credit card numbers are formatted correctly`(input: String, expected: String){
            // Given a formatting class
            val classToTest: Formatter = CardFormatter

            // When formatting is applied
            val formatted = classToTest.format(input)

            // then the value should be formatted correctly
            assert(formatted == expected)
        }

        @ParameterizedTest
        @ArgumentsSource(DateFormatterProvider::class)
        fun `dater inputs are formatted correctly`(format: DateFormat, separator: String, input: String, expected: String){
            // Given a formatting class
            val classToTest: Formatter = DateFormatter(dateFormat = format, separator = separator)

            // When formatting is applied
            val formatted = classToTest.format(input)

            // then the value should be formatted correctly
            assert(formatted == expected)
        }
    }
}
