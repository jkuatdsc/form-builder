package com.dsc.formbuilder.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dsc.formbuilder.theme.FormBuilderTheme

class ExitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormBuilderTheme {

            }
        }
    }
}