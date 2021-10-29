package com.dsc.formbuilder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        Column {
            val state by remember { mutableStateOf(FormState()) }
            Form(
                state = state,
                fields = listOf(
                    FormField(
                        name = "email",
                        validators = listOf(Required())
                    ),
                    FormField(
                        name = "phone",
                        validators = listOf(Required())
                    )
                )
            )
            Button(
                content = { Text("Click me") },
                onClick = {
                    if (state.validate()) {
                        Log.d("Validators", "onCreate: We are good to go")
                    } else {
                        Log.e("Validators", "Content: We are not good to go")
                    }
                }
            )
        }
    }
}
