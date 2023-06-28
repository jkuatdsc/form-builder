package com.dsc.form_builder.format

/**
 * These are the formatting options for the [DateFormatter] class.
 */
enum class DateFormat(val pattern: String) {
    DDMMYYYY("ddMMuuuu"),
    MMDDYYYY("MMdduuuu"),
    YYYYDDMM("uuuuddMM"),
    DDMMYY("ddMMuu"),
    MMDDYY("MMdduu"),
    YYMMDD("uuMMdd")
}

// Get the index where to place the separator
private fun DateFormat.separatorIndices(): MutableList<Int> {
    val indices = mutableListOf<Int>()
    val stringFormat = this.toString()
    stringFormat.forEachIndexed { index, char ->
        if (index > 0) {
            val prev = stringFormat[index - 1]

            if (prev != char) {
                if (indices.isNotEmpty()) indices.add(index + 1)
                else indices.add(index)
            }
        }
    }
    return indices
}


/**
 *
 * This formatter is used for date inputs. You need to specify a [DateFormat] and a separator.
 * The formatting function places the separator in the respective index as the user types.
 *
 * Note: character limiting is not supported in the formatter.
 */

class DateFormatter(private val dateFormat: DateFormat, private val separator: String) : Formatter {
    override fun format(value: String): String {
        var formatted = value
        val indices = dateFormat.separatorIndices()

        // add first separator if user has exceeded that index
        val first = indices.first()
        if (value.length > first) formatted = formatted.replaceRange(first, first, separator)

        // add last separator if user has exceeded that index
        val last = indices.last()
        if (value.length >= last) formatted = formatted.replaceRange(last, last, separator)

        return formatted
    }
}
