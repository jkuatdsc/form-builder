package com.dsc.form_builder

import kotlin.reflect.KClass

open class FormState(val fields: List<BaseState<*>>) {

    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    fun getState(name: String): BaseState<*> = fields.first { it.name == name }

    fun <T : Any> getData(dataClass: KClass<T>) : T {
        val map = fields.associate { it.name to it.getData() }
        val constructor = dataClass.constructors.last()
        val args = constructor.parameters.associateWith { map[it.name] }
        return constructor.callBy(args)
    }
}