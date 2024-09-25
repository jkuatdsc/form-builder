package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.format.CardFormatter
import com.dsc.form_builder.format.DateFormat
import com.dsc.form_builder.format.DateFormatter
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
fun PersonalDetails(formState: FormState<BaseState<*>>) {
    val emailState: TextFieldState = formState.getState("email")
    val cardState: TextFieldState = formState.getState("card")
    val dateState: TextFieldState = formState.getState("date")

    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Personal Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextInput(label = "Email", state = emailState)

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(
            label = "Card Number",
            state = cardState,
            visualTransformation = cardState.getTransformation()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextInput(
            label = "Date of birth",
            state = dateState,
            visualTransformation = dateState.getTransformation()
        )

    }
}

@Composable
fun TextInput(
    label: String,
    state: TextFieldState,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {

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
            singleLine = true,
            visualTransformation = visualTransformation,
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
            TextFieldState("email"),
            TextFieldState("phone"),
            TextFieldState(
                "date",
                formatter = DateFormatter(dateFormat = DateFormat.DDMMYYYY, separator = "/"),
            ),
            TextFieldState("card", formatter = CardFormatter),
        )
    )

    FormBuilderTheme {

        Surface(color = MaterialTheme.colors.background) {
            PersonalDetails(formState)
        }
    }
}
