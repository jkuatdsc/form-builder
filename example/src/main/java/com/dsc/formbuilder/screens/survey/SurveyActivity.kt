package com.dsc.formbuilder.screens.survey

import android.content.Intent
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
import com.dsc.formbuilder.screens.ExitActivity
import com.dsc.formbuilder.screens.survey.components.*
import com.dsc.formbuilder.theme.FormBuilderTheme

class SurveyActivity : ComponentActivity() {
    private val viewmodel: SurveyViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FormBuilderTheme {
                val screen by remember { viewmodel.screen }
                val formState = remember { viewmodel.formState }
                val validate = intent.getBooleanExtra("validate", true)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    TabLayout(screen = screen)

                    when(screen){
                        0 -> PersonalDetails(formState = formState)
                        1 -> TechnicalDetails(formState = formState)
                        2 -> OtherDetails(formState = formState)
                    }

                    Navigation(screen = screen) {
                        if (validate) {
                            viewmodel.validateScreen(screen)
                        } else {
                            if (screen == 2) viewmodel.validateSurvey()
                            else viewmodel.navigate(screen = screen + 1)
                        }
                    }
                }


                // Navigate to exit screen if everything is ok
                val finish by remember { viewmodel.finish }
                if (finish){
                    startActivity(Intent(this, ExitActivity::class.java))
                }
            }
        }
    }
}