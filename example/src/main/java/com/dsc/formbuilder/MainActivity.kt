package com.dsc.formbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.TextFieldState

class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val formState = remember { viewmodel.formState }
        val ageState = formState.getState("age")
        val emailState = formState.getState("email")
        val passwordState = formState.getState("password")
        val genderState = formState.getState("gender")
        val happinessState = formState.getState("happiness")
        val hobbiesState = formState.getState("hobbies")
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {


            OutlinedTextField(
                value = emailState.text,
                isError = emailState.hasError,
                label = { Text("Email address") },
                onValueChange = { emailState.change(it) }
            )
            if (emailState.hasError) Text(emailState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = passwordState.text,
                isError = passwordState.hasError,
                label = { Text("Password") },
                onValueChange = { passwordState.change(it) }
            )
            if (passwordState.hasError) Text(passwordState.errorMessage, color = Color.Red)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = ageState.text,
                isError = ageState.hasError,
                label = { Text("Age") },
                onValueChange = { ageState.change(it) }
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
                onClick = { viewmodel.submit() },
                content = { Text("submit") },
            )
        }
    }

    @Composable
    fun SelectGender(genderState: TextFieldState<*>) {
        val radioGroupOptions = listOf(
            "Male",
            "Female",
            "Non Binary",
            "Prefer not to say"
        )
        val selected = genderState.text
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
    fun HappinessIndex(happinessState: TextFieldState<*>) {
        val value = if (happinessState.text == "") 0.0f else happinessState.text.toFloat()
        Text(text = "Happiness level: ${(value * 100).toInt()}")
        Slider(
            value = value,
            onValueChange = { happinessState.change(it.toString()) })
    }

    @Composable
    fun Hobbies(hobbyState: TextFieldState<*>) {
        val hobbiesList = listOf("Chess", "Sky Diving", "Reading", "Travelling")
        val selectedHobbies = remember { hobbyState.text.split(",").toMutableList() }

        fun onHobbySelected(add: Boolean, text: String) {
            if (add) {
                selectedHobbies.add(text)
                hobbyState.change(selectedHobbies.toString())
            } else if (!add) {
                selectedHobbies.remove(text)
                hobbyState.change(selectedHobbies.toString())
            }
        }

        Text(text = "Hobbies")

        hobbiesList.forEach { hobby ->
            var isChecked by remember { mutableStateOf(false) }
            Row(Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                        onHobbySelected(it, text = hobby)
                    })
                Text(text = hobby, modifier = Modifier.padding(top = 14.dp))
            }
        }
    }
}
