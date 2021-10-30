package com.dsc.formbuilder

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun Form(state: FormState, fields: List<FormField>){

    state.setFields(fields)

    Column {
        fields.forEach {
            it.Content()
        }
    }
}