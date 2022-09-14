package com.dsc.formbuilder.screens.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dsc.form_builder.FormState
import com.dsc.formbuilder.screens.survey.components.*
import com.dsc.formbuilder.theme.FormBuilderTheme

class SurveyActivity : ComponentActivity() {
    private val viewmodel: SurveyViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FormBuilderTheme {
                val screen by remember { viewmodel.screen }
                val validate = intent.getBooleanExtra("validate", true)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    TabLayout(screen = screen)

//                    when(screen){
//                        0 -> PersonalDetails(formState = formstate)
//                        1 -> TechnicalDetails(formState = formstate)
//                        3 -> OtherDetails(formState = formstate)
//                    }

                    Navigation(screen = screen) {
                        if (validate) {
                            viewmodel.validateScreen(screen)
                        } else {
                            if (screen == 2) viewmodel.validateSurvey()
                            else viewmodel.navigate(screen = screen + 1)
                        }
                    }
                }

            }
        }
    }
}