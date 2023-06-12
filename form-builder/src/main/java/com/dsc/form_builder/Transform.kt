package com.dsc.form_builder

/**
 * This interface is used to allow change of data types to a suitable type when necessary.
 */

fun interface Transform<T> {
    /**
     * this abstract method is used to change the [value] data type to a desired type and return [Any]?.
     * @param value the value of type [T] to be parsed to [Any].
     * For example to read 'age' value from a field [TextFieldState], it will be transformed from [String] to [Int].
     */
    fun transform(value: T): Any?
}
