package com.dsc.form_builder

import kotlin.reflect.KClass
import kotlin.reflect.KParameter

/**
 * This class represents the state of the whole form, i.e, the whole collection of fields. It is used to manage all of the states in terms of accessing data and validations.
 * @param fields this is a list of all fields in the form. We pass them as a parameter to the constructor for ease of management and access.
 *
 * @author [Linus Muema](https://github.com/linusmuema)
 * @created 05/04/2022 - 10:00 AM
 */
open class FormState<T : BaseState<*>>(val fields: List<T>) {

    /**
     * This function is used to validate the whole form. It goes through all fields calling the [BaseState.validate] function. If all of them return true, then the function also returns true.
     */
    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    /**
     * This function gets a single field state. It uses the name specified in the [BaseState.name] field to find the field.
     * @param name the name of the field to get.
     */
    inline fun <reified u> getState(name: String): u = fields.first { it.name == name } as u

    /**
     * This function is used to access the data in the whole form. It goes through all fields calling the [BaseState.getData] function and stores them in a [Map] of [String] to [Any]. This map is then used in a constructor to create the specified class.
     * @param dataClass the class to create using the data in the form data.
     */
    fun <T : Any> getData(dataClass: KClass<T>): T {
        val map = fields.associate { it.name to it.getData() }
        val constructor = dataClass.constructors.last()
        val args: MutableMap<KParameter, Any?> = mutableMapOf()
        constructor.parameters.associateWith {
            val value = map[it.name]
            if (!it.isOptional) {
                checkNotNull(value) {
                    """
                        A non-null value (${it.name}) in your class doesn't have a matching field in the form data. 
                        This will throw a NullPointerException when creating $dataClass. To solve this, you can:
                        1. Make the value nullable in your data class
                        2. Provide a default value for the parameter
                    """.trimIndent()
                }
                args[it] = value
            }
        }
        return constructor.callBy(args)
    }

    /**
     * This function is used to reset data in all the form fields to their initial values.
     */
    fun reset() {
        fields.map {
            it.reset()
        }
    }
}
