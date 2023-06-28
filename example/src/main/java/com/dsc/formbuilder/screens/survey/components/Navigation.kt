package com.dsc.formbuilder.screens.survey.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.theme.FormBuilderTheme


@Composable
@Preview(showBackground = true)
fun NavigationPreview() {
    var screen by remember { mutableStateOf(0) }

    FormBuilderTheme {
        Navigation(screen = screen) {
            screen = if (screen == 2) 0 else screen + 1
        }
    }
}

@Composable
fun Navigation(screen: Int, navigate: () -> Unit) {
    val shape = MaterialTheme.shapes.large
    val white = MaterialTheme.colors.primary
    val color = MaterialTheme.colors.onPrimary
    val textStyle = MaterialTheme.typography.body1

    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = color)

    val text = if (screen != 2) "Next" else "Finish"

    Button(
        shape = shape,
        onClick = navigate,
        colors = buttonColors,
        modifier = Modifier.width(200.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 15.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, style = textStyle.copy(fontSize = 12.sp, color = white))

            Spacer(modifier = Modifier.width(15.dp))

            Icon(
                tint = white,
                contentDescription = "arrow",
                painter = painterResource(id = R.drawable.ic_arrow),
            )
        }
    }
}