package com.dsc.form_builder

import com.dsc.form_builder.format.DateFormat
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

object CreditCardFormatterProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("40128888888818", "4012 8888 8888 18"),
        Arguments.of("5555555555554444", "5555 5555 5555 4444"),
        Arguments.of("DE89370400440532013000", "DE89 3704 0044 0532 0130 00")
    )
}

object DateFormatterProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of(DateFormat.DDMMYY, ".", "010101", "01.01.01"),
        Arguments.of(DateFormat.DDMMYYYY, "-", "01012001", "01-01-2001")
    )
}
