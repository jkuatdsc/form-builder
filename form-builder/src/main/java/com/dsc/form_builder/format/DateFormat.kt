package com.dsc.form_builder.format

/**
 * These are the formatting options for the [DateFormatter] class.
 *
 * @author [Joy Kangangi](https://github.com/joykangangi)
 */
enum class DateFormat(val pattern: String) {
    DDMMYYYY("ddMMuuuu"),
    MMDDYYYY("MMdduuuu"),
    YYYYDDMM("uuuuddMM"),
    DDMMYY("ddMMuu"),
    MMDDYY("MMdduu"),
    YYMMDD("uuMMdd")
}

/**
 * Determines the indices where separators should be placed in a [DateFormat].
 *
 * This function iterates over the characters of the [DateFormat] string representation
 * and identifies the positions where the format changes, such as from one character type to another.
 * These positions are used to insert separators between different segments of the date format.
 *
 * @return A mutable list of integers representing the positions where separators should be placed.
 *
 * @author [Linus Muema](https://github.com/linusmuema)
 */
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
 *
 * @author [Linus Muema](https://github.com/linusmuema)
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
