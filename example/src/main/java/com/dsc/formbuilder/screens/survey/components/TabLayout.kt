package com.dsc.formbuilder.screens.survey.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.formbuilder.theme.FormBuilderTheme

@Composable
@Preview(showBackground = true)
fun TabLayoutPreview() {
    FormBuilderTheme {
        TabLayout(screen = 2)
    }
}

@Composable
fun TabLayout(screen: Int) {
    val black = MaterialTheme.colors.onPrimary
    val textStyle = MaterialTheme.typography.caption

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        var end by remember { mutableStateOf(0.0f) }
        var start by remember { mutableStateOf(0.0f) }
        var middle by remember { mutableStateOf(0.0f) }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextBox(onPlaced = { start = it }) {
                val fontWeight = if (screen == 0) FontWeight.Bold else FontWeight.Normal
                Text(
                    text = "Personal\nDetails",
                    textAlign = TextAlign.Center,
                    style = textStyle.copy(fontWeight = fontWeight),
                )
            }

            TextBox(onPlaced = { middle = it }) {
                AnimatedVisibility(visible = screen >= 1, enter = fadeIn(), exit = fadeOut()) {
                    val fontWeight = if (screen == 1) FontWeight.Bold else FontWeight.Normal
                    Text(
                        text = "Technical\nDetails",
                        textAlign = TextAlign.Center,
                        style = textStyle.copy(fontWeight = fontWeight),
                    )
                }
            }

            TextBox(onPlaced = { end = it }) {
                AnimatedVisibility(visible = screen >= 2, enter = fadeIn(), exit = fadeOut()) {
                    val fontWeight = if (screen == 2) FontWeight.Bold else FontWeight.Normal
                    Text(
                        text = "Other\nDetails",
                        textAlign = TextAlign.Center,
                        style = textStyle.copy(fontWeight = fontWeight),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Box(modifier = Modifier.offset())

        Row(modifier = Modifier.fillMaxWidth()) {
            Canvas(modifier = Modifier) {
                drawLine(
                    color = black,
                    end = Offset(x = end + 4, y = 0f),
                    start = Offset(x = start + 4, y = 0f),
                )
            }
            Canvas(modifier = Modifier) {
                translate(left = start + 4) {
                    drawCircle(
                        color = black,
                        radius = if (screen == 0) 16f else 10f
                    )
                }
            }

            Canvas(modifier = Modifier) {
                translate(left = middle + 4) {
                    drawCircle(
                        color = black,
                        radius = if (screen == 1) 16f else 10f
                    )
                }
            }

            Canvas(modifier = Modifier) {
                translate(left = end + 4) {
                    drawCircle(
                        color = black,
                        radius = if (screen == 2) 16f else 10f
                    )
                }
            }
        }

    }
}

@Composable
fun TextBox(onPlaced: (Float) -> Unit, content: @Composable () -> Unit) {
    Box(
        content = { content() },
        modifier = Modifier
            .width(75.dp)
            .height(45.dp)
            .padding(8.dp)
            .onGloballyPositioned { onPlaced(it.getCenter()) },
        contentAlignment = Alignment.Center,
    )
}

fun LayoutCoordinates.getCenter(): Float {
    val bounds = this.boundsInRoot()
    val half = (bounds.right - bounds.left) / 2
    return half + bounds.left
}