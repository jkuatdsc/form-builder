package com.dsc.formbuilder

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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

            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { viewmodel.submit() },
                content = { Text("submit") },
            )
        }
    }
}
