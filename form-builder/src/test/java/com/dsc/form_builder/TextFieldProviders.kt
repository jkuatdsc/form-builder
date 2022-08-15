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