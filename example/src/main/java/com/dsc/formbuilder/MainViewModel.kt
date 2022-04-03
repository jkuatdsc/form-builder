package com.dsc.formbuilder

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators

class MainViewModel : ViewModel() {

    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "email",
                transform = { it.trim().lowercase() },
                validators = listOf(Validators.Email()),
            ),
            TextFieldState(
                name = "password",
                validators = listOf(Validators.Required())
            ),
            TextFieldState(
                name = "age",
                transform = { it.toInt() },
                validators = listOf(Validators.MinValue(limit = 18, message = "too young"))
            ),
            TextFieldState(
                name = "gender",
                validators = listOf(Validators.Required(message = "you need to specify your gender"))
            ),
            TextFieldState(
                name = "happiness",
                transform = { it.toFloat() },
                validators = listOf(Validators.Required(message = "how happy are you?"))
            ),
            SelectState(
                name = "hobbies",
                validators = listOf(
                    Validators.Required(message = "pick at least one hobby")
                )
            )
        )
    )

    fun submit() {
        if (formState.validate()) {
            val data = formState.getData(Credentials::class)
            Log.d("Data", "submit: data from the form $data")
        }
    }

}