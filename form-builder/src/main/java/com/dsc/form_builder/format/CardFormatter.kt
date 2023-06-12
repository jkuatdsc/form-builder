package com.dsc.form_builder.format

/**
 *
 * This formatter is used for credit/debit card numbers. Can also be used for IBAN numbers.
 * It groups the input value into chunks of four characters.
 * The separator is an empty space as this is the most common option.
 *
 * Note: character limiting is not supported in the formatter.
 */
object CardFormatter: Formatter {
    override fun format(value: String): String {
        return value.chunked(4).joinToString(" ")
    }
}
