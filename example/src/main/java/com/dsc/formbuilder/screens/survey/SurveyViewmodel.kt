package com.dsc.formbuilder.screens.survey

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.dsc.form_builder.format.CardFormatter
import com.dsc.form_builder.format.DateFormat
import com.dsc.form_builder.format.DateFormatter
import com.dsc.formbuilder.screens.survey.components.SurveyModel

class SurveyViewmodel : ViewModel() {

    private val _screen: MutableState<Int> = mutableStateOf(0)
    val screen: State<Int> = _screen

    private val _finish: MutableState<Boolean> = mutableStateOf(false)
    val finish: State<Boolean> = _finish

    val formState: FormState<BaseState<*>> = FormState(
        fields = listOf(
            TextFieldState(
                name = "email",
                validators = listOf(
                    Validators.Email(),
                    Validators.Required(),
                ),
                transform = { it.trim().lowercase() },
            ),
            TextFieldState(
                name = "card",
                formatter = CardFormatter,
                validators = listOf(
                    Validators.CardNumber(),
                    Validators.Required(),
                ),
            ),
            TextFieldState(
                name = "date",
                formatter = DateFormatter(dateFormat = DateFormat.DDMMYYYY, separator = "/"),
                validators = listOf(
                    Validators.Date(format = DateFormat.DDMMYYYY),
                    Validators.Required(),
                ),
            ),
            SelectState(
                name = "platform",
                validators = listOf(
                    Validators.Required(
                        message = "Select at least one platform"
                    )
                ),
            ),
            SelectState(
                name = "language",
                validators = listOf(
                    Validators.Required(
                        message = "Select at least one language"
                    )
                )
            ),
            SelectState(
                name = "ide",
                validators = listOf(
                    Validators.Required(
                        message = "Select at least one IDE"
                    )
                )
            ),
            ChoiceState(
                name = "gender",
                validators = listOf(
                    Validators.Required(
                        message = "Select your gender"
                    )
                )
            ),
            ChoiceState(
                name = "experience",
                validators = listOf(
                    Validators.Required(
                        message = "Select your experience"
                    )
                )
            ),
            ChoiceState(
                name = "os",
                validators = listOf(
                    Validators.Required(
                        message = "Select one system"
                    )
                )
            )
        )
    )

    fun navigate(screen: Int) {
        _screen.value = screen
    }

    fun validateSurvey() {
        val pages: List<List<Int>> = (0..5).chunked(3)
        if (!formState.validate()) {
            val position = formState.fields.indexOfFirst { it.hasError }
            _screen.value = pages.indexOfFirst { it.contains(position) }
        } else {
            logData()
            _finish.value = true
        }
    }

    fun validateScreen(screen: Int) {
        val fields: List<BaseState<*>> = formState.fields.chunked(3)[screen]
        if (fields.map { it.validate() }
                .all { it }) { // map is used so we can execute validate() on all fields in that screen
            if (screen == 2) {
                logData()
                _finish.value = true
            }
            _screen.value += 1
        }
    }

    private fun logData() {
        val data = formState.getData(SurveyModel::class)
        Log.d("SurveyLog", "form data is $data")
    }
}
