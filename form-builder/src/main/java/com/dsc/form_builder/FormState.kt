package com.dsc.form_builder

import kotlin.reflect.KClass

open class FormState(val fields: List<TextFieldState<*>>) {

    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    fun getState(name: String): TextFieldState<*> = fields.first { it.name == name }

    fun <T : Any> getData(dataClass: KClass<T>) : T {
        val map = fields.associate {
            val value = if (it.transform == null) it.text else it.transform!!(it.text)
            it.name to value
        }

        val constructor = dataClass.constructors.last()
        val args = constructor.parameters.associateWith { map[it.name] }
        return constructor.callBy(args)
    }
}