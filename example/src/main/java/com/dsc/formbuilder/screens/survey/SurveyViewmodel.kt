package com.dsc.formbuilder.screens.survey

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.*

class SurveyViewmodel : ViewModel() {

    private val _screen: MutableState<Int> = mutableStateOf(0)
    val screen: State<Int> = _screen

    private val _finish: MutableState<Boolean> = mutableStateOf(false)
    val finish: State<Boolean> = _finish

    val formState: FormState<BaseState<*>> = FormState(
        fields = listOf(
            TextFieldState(
                name = "username",
                validators = listOf(
                    Validators.Min(
                        limit = 4,
                        message = "Username should have more than 4 characters"
                    ),
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "email",
                validators = listOf(
                    Validators.Required(),
                    Validators.Email()
                ),
                transform = { it.trim().lowercase() }
            ),
            TextFieldState(
                name = "number",
                validators = listOf(
                    Validators.Required(),
                    Validators.Custom(
                        message = "enter your number in the format +254xxxxxxxxx",
                        function = { correctNum(it.toString()) }
                    )
                )
            ),
            SelectState(
                name = "platform",
                validators = listOf(Validators.Required(message = "pick at least one platform"))
            ),
            SelectState(
                name = "language",
                validators = listOf(Validators.Required(message = "pick at least one language"))
            ),
            SelectState(
                name = "ide",
                validators = listOf(Validators.Required(message = "pick at least one IDE"))
            ),
            ChoiceState(
                name = "gender",
                validators = listOf(Validators.Required(message = "please select your gender"))
            ),
            ChoiceState(
                name = "experience",
                validators = listOf(Validators.Required(message = "please select your experience"))
            ),
            ChoiceState(
                name = "os",
                validators = listOf(Validators.Required(message = "please select one O.S"))
            )
        )
    )

    fun navigate(screen: Int) {
        _screen.value = screen
    }

    fun validateSurvey() {
        val pages: List<List<Int>> = (0..5).chunked(3)
        if (!formState.validate()){
            val position = formState.fields.indexOfFirst { it.hasError }
            _screen.value = pages.indexOfFirst { it.contains(position) }
        } else _finish.value = true
    }

    fun validateScreen(screen: Int) {
        val fields: List<BaseState<*>> = formState.fields.chunked(3)[screen]
        if (fields.all { it.validate() }){
            if (screen == 2) _finish.value = true
            _screen.value +=1
        }
    }

    private fun correctNum(value: String): Boolean {
        return value.length == 13
    }

}