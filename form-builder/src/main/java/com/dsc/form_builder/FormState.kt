package com.dsc.form_builder

import kotlin.reflect.KClass

open class FormState<T : BaseState<*>>(val fields: List<T>) {

    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    inline fun <reified u> getState(name: String): u = fields.first { it.name == name } as u

    fun <T : Any> getData(dataClass: KClass<T>): T {
        val map = fields.associate { it.name to it.getData() }
        val constructor = dataClass.constructors.last()
        val args = constructor.parameters.associateWith { map[it.name] }
        return constructor.callBy(args)
    }
}