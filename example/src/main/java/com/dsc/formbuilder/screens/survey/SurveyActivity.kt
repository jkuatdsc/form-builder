package com.dsc.formbuilder.screens.survey

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
                    TabLayout(screen = screen)

                    Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(12.dp)) {
                        when(screen){
                            0 -> PersonalDetails(formState = formState)
                            1 -> TechnicalDetails(formState = formState)
                            2 -> OtherDetails(formState = formState)
                        }

                        Spacer(modifier = Modifier.size(32.dp))

                        Navigation(screen = screen) {
                            if (validate) {
                                viewmodel.validateScreen(screen)
                            } else {
                                if (screen == 2) viewmodel.validateSurvey()
                                else viewmodel.navigate(screen = screen + 1)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(100.dp))
                }


                // Navigate to exit screen if everything is ok
                val processDone by remember { viewmodel.finish }
                if (processDone){
                    startActivity(Intent(this, ExitActivity::class.java))
                    finish()
                }
            }
        }
    }
}

