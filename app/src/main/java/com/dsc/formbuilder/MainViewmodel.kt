package com.dsc.formbuilder

import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators

class MainViewmodel : ViewModel() {

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
        )
    )

    fun submit(){
        if (formState.validate()){
            val data = formState.getData(Credentials::class)
            Log.d("Data", "submit: data from the form $data")
        }
    }

}