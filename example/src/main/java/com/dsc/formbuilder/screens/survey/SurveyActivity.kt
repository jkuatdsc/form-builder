package com.dsc.formbuilder.screens.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dsc.formbuilder.theme.FormBuilderTheme

class SurveyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormBuilderTheme {

            }
        }
    }
}