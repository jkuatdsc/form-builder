package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
fun PersonalDetails(formState: FormState<BaseState<*>>) {
    val usernameState: TextFieldState = formState.getState("username")
    val emailState: TextFieldState = formState.getState("email")
    val numberState: TextFieldState = formState.getState("number")

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
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
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.onPrimary,
            unfocusedBorderColor = MaterialTheme.colors.onPrimary
        )
    )
}


@Composable
@Preview
fun PersonalDetailsPreview() {
    val formState: FormState<BaseState<*>> = FormState(
        listOf(
            TextFieldState("username"),
            TextFieldState("email"),
            TextFieldState("number")
        )
    )

    FormBuilderTheme {

        Surface(color = MaterialTheme.colors.background) {
            PersonalDetails(formState)
        }
    }
}
