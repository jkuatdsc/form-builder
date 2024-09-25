package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.form_builder.BaseState
import com.dsc.form_builder.ChoiceState
import com.dsc.form_builder.FormState
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
fun OtherDetails(formState: FormState<BaseState<*>>) {
	
	val genderState: ChoiceState = formState.getState("gender")
	val experienceState: ChoiceState = formState.getState("experience")
	val osState: ChoiceState = formState.getState("os")
	
	
	val genderOptions = listOf(
		"Non-binary",
		"Male",
		"Female"
	)
	val experienceOptions = listOf(
		"1-3 Years",
		"3-5 Years",
		"5+ Years"
	)
	val osOptions = listOf(
		"Mac OS",
		"Linux",
		"Windows"
	)
	
	Column(
		horizontalAlignment = CenterHorizontally,
		verticalArrangement = Center
	) {
		Text(
			modifier = Modifier.fillMaxWidth(),
			text = "Other Details",
			style = MaterialTheme.typography.h6
		)
		
		Spacer(modifier = Modifier.height(30.dp))
		
		OtherDetailsRow(
			labelText = "Gender",
			items = genderOptions,
			genderState
		)
		OtherDetailsRow(
			labelText = "Experience",
			items = experienceOptions,
			experienceState
		)
		OtherDetailsRow(
			labelText = "Using OS",
			items = osOptions,
			osState
		)
	}
}

@Composable
fun OtherDetailsRow(
	labelText: String,
	items: List<String>,
	state: ChoiceState
) {
	Column {
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = labelText,
				style = MaterialTheme.typography.body1
			)
			
			Spacer(modifier = Modifier.weight(1f))
			
			items.forEach { item ->
				Column(
					modifier = Modifier
                        .width(85.dp)
                        .selectableGroup(),
					verticalArrangement = Center,
					horizontalAlignment = CenterHorizontally
				) {
					Text(
						text = item,
						style = MaterialTheme.typography.caption
					)
					RadioButton(
						selected = state.value == item,
						onClick = { state.change(item) },
						colors = RadioButtonDefaults.colors(
							unselectedColor = MaterialTheme.colors.onPrimary,
							selectedColor = MaterialTheme.colors.onPrimary
						)
					)
				}
			}
		}
		
		if (state.hasError) {
			Text(
				text = state.errorMessage,
				style = MaterialTheme.typography.caption.copy(
					color = MaterialTheme.colors.error
				)
			)
		}
	}
}

@Preview
@Composable
fun OtherDetailsPreview() {
	val formState: FormState<BaseState<*>> = FormState(
		listOf(
			ChoiceState(
				"gender",
				validators = listOf()
			),
			ChoiceState(
				"experience",
				validators = listOf()
			),
			ChoiceState(
				"os",
				validators = listOf()
			)
		)
	)
	FormBuilderTheme {
		Surface(color = MaterialTheme.colors.background) {
			OtherDetails(formState)
		}
	}
}