package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
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

    Column(verticalArrangement = Center, horizontalAlignment = CenterHorizontally) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Personal Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextInput(label = "Username", state = usernameState)

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(label = "Email", state = emailState)

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(label = "Number", state = numberState)

    }
}

@Composable
fun TextInput(label: String, state: TextFieldState) {

    Column {
        OutlinedTextField(
            value = state.value,
            isError = state.hasError,
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            onValueChange = { state.change(it) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                errorBorderColor = MaterialTheme.colors.error,
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary
            ),
        )

        if (state.hasError) {
            Text(
                text = state.errorMessage,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.error
                )
            )
        }
    }
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
