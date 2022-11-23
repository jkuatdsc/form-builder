package com.dsc.formbuilder.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.screens.survey.SurveyActivity
import com.dsc.formbuilder.theme.FormBuilderTheme

class IntroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormBuilderTheme {
                val screenState = remember {
                    MutableTransitionState(false).apply { targetState = true }
                }
                AnimatedVisibility(
                    visibleState = screenState,
                    content = { IntroScreen() },
                    enter = fadeIn(),
                    exit = fadeOut()
                )
            }
        }
    }
}

@Composable
fun IntroScreen(modifier: Modifier = Modifier) {
    val black = MaterialTheme.colors.onPrimary
    val white = MaterialTheme.colors.background

    val context = LocalContext.current
    var checked by remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
            val infiniteTransition = rememberInfiniteTransition()
            val animationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )

            val pulseMagnitude by infiniteTransition.animateFloat(
                initialValue = 240f,
                targetValue = 270f,
                animationSpec = animationSpec
            )

            val textSizeInfinite = infiniteTransition.animateFloat(
                initialValue = MaterialTheme.typography.h4.fontSize.value,
                targetValue = MaterialTheme.typography.h3.fontSize.value,
                animationSpec = animationSpec
            )

            Card(
                elevation = 5.dp,
                shape = CircleShape,
                modifier = Modifier
                    .size(pulseMagnitude.dp)
                    .padding(all = 12.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            val intent = Intent(context, SurveyActivity::class.java)
                            intent.putExtra("validate", checked)
                            context.startActivity(intent)
                        },
                    ),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.start),
                        style = MaterialTheme.typography.button.copy(
                            fontSize = textSizeInfinite.value.sp
                        )
                    )
                }
            }
        }

        Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.BottomCenter) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.validate),
                    style = MaterialTheme.typography.body1,
                )
                Spacer(modifier = modifier.width(24.dp))

                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = black,
                        uncheckedTrackColor = white,
                        uncheckedThumbColor = black,
                        uncheckedBorderColor = black,
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun IntroPreview() {
    FormBuilderTheme {
        IntroScreen()
    }
}