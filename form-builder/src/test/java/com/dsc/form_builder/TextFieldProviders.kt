package com.dsc.form_builder

import com.dsc.form_builder.format.DateFormat
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
        Arguments.of("4012888888881881", true), // Visa card
        Arguments.of("5105105105105100", true), // Mastercard
        Arguments.of("374245455400126", true) // AMEX
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

object DateArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of("12122012", DateFormat.DDMMYYYY, true),
        Arguments.of("06312023", DateFormat.MMDDYYYY, false),
        Arguments.of("20231504", DateFormat.YYYYDDMM, true),
        Arguments.of("290213", DateFormat.DDMMYY, false), // Non Leap Year
        Arguments.of("121323", DateFormat.MMDDYY, true),
        Arguments.of("070626", DateFormat.YYMMDD, true)
    )
}
