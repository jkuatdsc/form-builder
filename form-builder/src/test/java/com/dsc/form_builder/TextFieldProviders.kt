package com.dsc.form_builder

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

object EmailArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("test@", false),
        Arguments.of("test@mail", false),
        Arguments.of("test@mail.com", true),
    )
}

object PhoneArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("test", false),
        Arguments.of("88005553535", true),
        Arguments.of("+8(800) 555-35-35", true),
        Arguments.of("8-800-555-35-35-", false),
    )
}

object WebUrlArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("test", false),
        Arguments.of("htt://www.test.com", false),
        Arguments.of("http://test.com", true),
        Arguments.of("https://www.test.com", true),
        Arguments.of("www.test.", false),
        Arguments.of("www.test.com.mx", true),
        Arguments.of("https://", false),
        Arguments.of("www.test.com", true),
    )
}

object CardNumberArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("1111111111111111", false),
        Arguments.of("1111", false),
        Arguments.of("4548111111111111", true)
    )
}

object MinCharsArgumentsProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("test", 2, true),
        Arguments.of("test", 4, true),
        Arguments.of("test", 6, false),
    )
}

object MaxCharsArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("test", 6, true),
        Arguments.of("test", 4, true),
        Arguments.of("test", 2, false),
    )
}

object MinValueArgumentsProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("20", 15, true),
        Arguments.of("20", 20, true),
        Arguments.of("20", 25, false),
        Arguments.of("test", 25, false),
    )
}

object MaxValueArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("20", 25, true),
        Arguments.of("20", 20, true),
        Arguments.of("20", 15, false),
        Arguments.of("test", 15, false),
    )
}