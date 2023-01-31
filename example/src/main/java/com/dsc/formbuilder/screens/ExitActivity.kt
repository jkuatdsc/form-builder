package com.dsc.formbuilder.screens

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.theme.FormBuilderTheme

class ExitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 3000)

        setContent {
            FormBuilderTheme {
                val screenState = remember {
                    MutableTransitionState(false).apply { targetState = true }
                }
                AnimatedVisibility(
                    visibleState = screenState,
                    content = { ExitScreen() },
                    enter = fadeIn(),
                    exit = fadeOut()
                )
            }
        }
    }
}

@Composable
fun ExitScreen() {
    val textStyle = MaterialTheme.typography.body1
    val infiniteTransition = rememberInfiniteTransition()

    val configuration = LocalConfiguration.current
    val handSize: Dp = when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            200.dp
        } else-> {
            300.dp
        }
    }

    val imageAnim by infiniteTransition.animateFloat(
        initialValue = 210f,
        targetValue = 240f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.padding(12.dp).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier)
        Box(modifier = Modifier.size(handSize), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.like),
                contentDescription = "Thumbs Up",
                modifier = Modifier.size(imageAnim.dp)
            )
        }
        Text(text = stringResource(id = R.string.thanks), style = textStyle)
        Spacer(modifier = Modifier)
    }
}

@Preview
@Composable
fun ExitPreview() {
    FormBuilderTheme {
        ExitScreen()
    }
}