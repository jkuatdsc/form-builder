package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.TextFieldState
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
fun PersonalDetails(
    usernameState: TextFieldState,
    emailState: TextFieldState,
    numberState: TextFieldState
) {
    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Personal Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextInput(label = "Username", state = usernameState)
        if (usernameState.hasError) Text(usernameState.errorMessage, color = Color.Red)

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(label = "Email", state = emailState)
        if (emailState.hasError) Text(emailState.errorMessage, color = Color.Red)

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(label = "Number", state = numberState)
        if (numberState.hasError) Text(numberState.errorMessage, color = Color.Red)
    }
}

@Composable
fun TextInput(label: String, state: TextFieldState) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        value = state.value,
        onValueChange = { state.change(it) },
        isError = state.hasError,
        label = { Text(text = label) })
}


@Composable
@Preview
fun PersonalDetailsPreview() {

    val prevState1 = TextFieldState(name = "prev1")
    val prevState2 = TextFieldState(name = "prev2")
    val prevState3 = TextFieldState(name = "prev3")

    FormBuilderTheme {
        PersonalDetails(
            emailState = prevState1,
            usernameState = prevState2,
            numberState = prevState3
        )
    }
}

@Composable
fun TechnicalDetails(
    platformSelectState: SelectState,
    languageSelectState: SelectState,
    ideSelectState: SelectState
) {

    val platforms = listOf("Android", "IOS", "Web")
    val languages = listOf("Javascript", "Kotlin", "Swift")
    val ides = listOf("Android Studio", "Xcode", "Vs code")

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Technical Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        TechnicalDetailsRow(labelText = "Used platform", platforms, platformSelectState)
        TechnicalDetailsRow(labelText = "Used languages", languages, languageSelectState)
        TechnicalDetailsRow(labelText = "Used IDE", ides, ideSelectState)
    }

}

@Composable
fun TechnicalDetailsRow(labelText: String, items: List<String>, state: SelectState) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = labelText, style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.weight(1f))

        items.forEach { item ->
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .selectableGroup(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.caption
                )
                Checkbox(
                    checked = state.value.contains(item),
                    onCheckedChange = {
                        if (it) state.select(item) else state.unselect(item)
                    },
                    colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colors.onPrimary)
                )
            }
        }
    }
}

@Preview
@Composable
fun TechnicalDetailsPreview() {
    val prevState = SelectState(name = "prev")
    FormBuilderTheme {
        TechnicalDetails(prevState, prevState, prevState)
    }
}

@Composable
fun OtherDetails(genderState: ChoiceState, experienceState: ChoiceState, osState: ChoiceState) {
    val genderOptions = listOf("Non-binary", "Male", "Female")
    val experienceOptions = listOf("1-3 Years", "3-5 Years", "5+ Years")
    val osOptions = listOf("Mac OS", "Linux", "Windows")
    Column(
        modifier = Modifier.padding(12.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Other Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        OtherDetailsRow(labelText = "Gender", items = genderOptions, genderState)
        OtherDetailsRow(labelText = "Experience", items = experienceOptions, experienceState)
        OtherDetailsRow(labelText = "Using OS", items = osOptions, osState)
    }
}

@Composable
fun OtherDetailsRow(labelText: String, items: List<String>, state: ChoiceState) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = labelText, style = MaterialTheme.typography.body1)

        Spacer(modifier = Modifier.weight(1f))

        items.forEach { item ->
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .selectableGroup(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.caption
                )
                RadioButton(
                    selected = state.value == item,
                    onClick = { state.change(item) },
                    colors = RadioButtonDefaults.colors(unselectedColor = MaterialTheme.colors.onPrimary)
                )
            }
        }
    }
}


@Preview
@Composable
fun OtherDetailsPreview() {
    val prevState1 = ChoiceState(name = "prev1", validators = listOf())
    val prevState2 = ChoiceState(name = "prev2", validators = listOf())
    val prevState3 = ChoiceState(name = "prev3", validators = listOf())
    FormBuilderTheme {
        OtherDetails(
            prevState1,
            prevState2,
            prevState3
        )
    }
}