package com.dsc.formbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.*

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val formState: FormState<BaseState<out Any>> = remember { viewModel.formState }
        val ageState: TextFieldState = formState.getState("age")
        val genderState: ChoiceState = formState.getState("gender")
        val emailState: TextFieldState = formState.getState("email")
        val passwordState: TextFieldState = formState.getState("password")
        val happinessState: TextFieldState = formState.getState("happiness")
        val hobbiesState: SelectState = formState.getState<SelectState>("hobbies")

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            OutlinedTextField(
                value = emailState.value,
                isError = emailState.hasError,
                label = { Text("Email address") },
                onValueChange = { emailState.change(it) }
            )

            if (emailState.hasError) Text(emailState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = passwordState.value,
                isError = passwordState.hasError,
                label = { Text("Password") },
                onValueChange = { passwordState.change(it) }
            )

            if (passwordState.hasError) Text(passwordState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = ageState.value,
                isError = ageState.hasError,
                label = { Text("Age") },
                onValueChange = { ageState.change(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (ageState.hasError) Text(ageState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            SelectGender(genderState = genderState)

            if (genderState.hasError) Text(genderState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))
            HappinessIndex(happinessState = happinessState)

            if (happinessState.hasError) Text(happinessState.errorMessage, color = Color.Red)

            Hobbies(hobbyState = hobbiesState)

            if (hobbiesState.hasError) Text(hobbiesState.errorMessage, color = Color.Red)

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { viewModel.submit() },
                content = { Text("submit") },
            )
        }
    }

    @Composable
    fun SelectGender(genderState: ChoiceState) {
        val radioGroupOptions = listOf(
            "Male",
            "Female",
            "Non Binary",
            "Prefer not to say"
        )
        val selected = genderState.value
        val onSelectedChanged = { text: String -> genderState.change(text) }
        Column(
            modifier = Modifier
                .selectableGroup()
        ) {
            radioGroupOptions.forEach {
                Row(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = selected == it,
                        onClick = { onSelectedChanged(it) },
                    )
                    Text(text = it, modifier = Modifier.padding(top = 14.dp))
                }
            }

        }
    }

    @Composable
    fun HappinessIndex(happinessState: TextFieldState) {
        val value = if (happinessState.value == "") 0.0f else happinessState.value.toFloat()
        Text(text = "Happiness level: ${(value * 100).toInt()}")
        Slider(
            value = value,
            onValueChange = { happinessState.change(it.toString()) })
    }

    @Composable
    fun Hobbies(hobbyState: SelectState) {
        val hobbiesList = listOf(
            "Chess",
            "Sky Diving",
            "Reading",
            "Travelling",
            "Bike Riding",
            "Mountain Climbing"
        )
        Text(text = "Hobbies")

        hobbiesList.forEach { hobby ->
            Row(Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = hobbyState.value.contains(hobby),
                    onCheckedChange = {
                        if (it) hobbyState.select(hobby) else hobbyState.unselect(hobby)
                    }
                )
                Text(text = hobby, modifier = Modifier.padding(top = 14.dp))
            }
        }
    }
}
