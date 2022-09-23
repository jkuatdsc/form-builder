package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.FormState
import com.dsc.form_builder.SelectState
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
fun TechnicalDetails(formState: FormState<BaseState<*>>) {

    val platformSelectState: SelectState = formState.getState("platform")
    val languageSelectState: SelectState = formState.getState("language")
    val ideSelectState: SelectState = formState.getState("ide")

    val platforms = listOf("      Android      ", "IOS", "Web")
    val languages = listOf("    Javascript    ", "Kotlin", "Swift")
    val ides = listOf("Android Studio", "Xcode", "Vs code")

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Technical Details",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(30.dp))

        TechnicalDetailsRow(labelText = "Used platform", platforms, platformSelectState)
        TechnicalDetailsRow(labelText = "Used languages", languages, languageSelectState)
        TechnicalDetailsRow(labelText = "Used IDE", ides, ideSelectState)
    }

}


@Composable
fun TechnicalDetailsRow(labelText: String, items: List<String>, state: SelectState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = labelText, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.weight(1f))

            items.forEach { item ->
                Column(
                    modifier = Modifier.width(85.dp).selectableGroup(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.caption
                    )
                    Checkbox(
                        checked = state.value.contains(item),
                        onCheckedChange = {
                            if (it) state.select(item) else state.unselect(item)
                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = MaterialTheme.colors.onPrimary,
                            checkedColor = MaterialTheme.colors.onPrimary
                        )
                    )
                }
            }
        }

        if (state.hasError) {
            Text(
                text = state.errorMessage,
                style = MaterialTheme.typography.caption.copy(
                    color = Color.Red
                )
            )
        }
    }
}


@Preview(device = Devices.PIXEL_3)
@Composable
fun TechnicalDetailsPreview() {

    val formState: FormState<BaseState<*>> = FormState(
        listOf(
            SelectState("platform"),
            SelectState("language"),
            SelectState("ide")
        )
    )
    FormBuilderTheme {

        Surface(color = MaterialTheme.colors.background) {
            TechnicalDetails(formState)
        }
    }
}