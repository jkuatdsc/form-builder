package com.dsc.formbuilder.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
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
                    MutableTransitionState(false).apply {
                        targetState = true
                    }
                }
                AnimatedVisibility(
                    visibleState = screenState,
                    enter = slideInHorizontally(),
                    exit = slideOutHorizontally()
                ) {
                    Surface(color = MaterialTheme.colors.background) {
                        IntroScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun IntroScreen(modifier: Modifier = Modifier) {
    val black = MaterialTheme.colors.onPrimary
    val white = MaterialTheme.colors.background
    val context = LocalContext.current
    val intent = Intent(context, SurveyActivity::class.java)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(150.dp))

        Box(
            modifier
                .size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            //this box prevents the animation from mis-positioning the other elements in the column

            val infiniteTransition = rememberInfiniteTransition()

            val pulseMagnitude by infiniteTransition.animateFloat(
                initialValue = 240f,
                targetValue = 270f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val textSizeInfinite = infiniteTransition.animateFloat(
                initialValue = MaterialTheme.typography.h4.fontSize.value,
                targetValue = MaterialTheme.typography.h3.fontSize.value,
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // The start Button
            Card(
                modifier
                    .height(pulseMagnitude.dp)
                    .width(pulseMagnitude.dp)
                    .padding(all = 12.dp)
                    .background(color = white)
                    .clickable(onClick = {
                        context.startActivity(intent)
                    }),
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
        Box(
            modifier = modifier
                .size(300.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var switchOn by remember { mutableStateOf(true) }
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
                    modifier = modifier.semantics { contentDescription = "Switch" },
                    checked = switchOn,
                    onCheckedChange = { switchOn = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = black
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
        Surface(color = MaterialTheme.colors.background) {
            IntroScreen()
        }
    }
}