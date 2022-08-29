package com.dsc.formbuilder.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.theme.FormBuilderTheme
import com.dsc.formbuilder.theme.white
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@Composable
fun PulsingButton(modifier: Modifier = Modifier, onStartClicked: () -> Unit) {
    val white = MaterialTheme.colors.background

    val infiniteTransition = rememberInfiniteTransition()
    val pulseMagnitude by infiniteTransition.animateFloat(
        initialValue = 240f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val textSizeInfinite = infiniteTransition.animateFloat(
        initialValue = MaterialTheme.typography.h4.fontSize.value,
        targetValue = MaterialTheme.typography.h3.fontSize.value,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // The start Button

        Card(
            modifier
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp)
                .padding(all = 12.dp)
                .background(color = white)
                .clickable(onClick = onStartClicked ),
            elevation = 5.dp,
            shape = CircleShape
        ) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = textSizeInfinite.value.sp
                )
            }
        }
    }

@Preview
@Composable
fun PulsePreview() {
    FormBuilderTheme {
        Surface(color = white) {
            PulsingButton(onStartClicked = { })
        }
    }
}