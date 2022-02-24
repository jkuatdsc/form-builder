package com.dsc.form_builder

fun interface Transform<T> {
    fun transform(value: T): Any?
}