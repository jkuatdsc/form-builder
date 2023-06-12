package com.dsc.form_builder

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

object MinArgumentsProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of(mutableListOf("item 1"), 2, false),
        Arguments.of(mutableListOf("item 1", "item 2"), 2, true),
        Arguments.of(mutableListOf("item 1", "item 2", "item 3"), 2, true),
    )
}

object MaxArgumentsProvider: ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> = Stream.of(
        Arguments.of(mutableListOf("item 1"), 2, true),
        Arguments.of(mutableListOf("item 1", "item 2"), 2, true),
        Arguments.of(mutableListOf("item 1", "item 2", "item 3"), 2, false),
    )
}
