package com.dsc.formbuilder.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.theme.FormBuilderTheme

class ExitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val finishActivity: () -> Unit = {
            this.finish()
            Log.d("ExitActivity", "Finish Called")
        }
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
                        ExitScreen(activityFinish = finishActivity)
                    }
                }
            }
        }
    }
}

@Composable
fun ExitScreen(modifier: Modifier = Modifier, activityFinish:()->Unit) {
    val black = MaterialTheme.colors.onPrimary
    val white = MaterialTheme.colors.background
    val textStyle = MaterialTheme.typography.body1
    val context = LocalContext.current
    val intent = Intent(context, IntroActivity::class.java)
    val infiniteTransition = rememberInfiniteTransition()

    val imageAnim by infiniteTransition.animateFloat(
        initialValue = 210f,
        targetValue = 240f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    val angleOffset = 15f
    val angleAnimation by infiniteTransition.animateFloat(
        initialValue = -angleOffset,
        targetValue = angleOffset ,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier
            .padding(12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = modifier.size(300.dp),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.like),
                contentDescription = "Thumbs Up",
                modifier = modifier.size(imageAnim.dp).rotate(angleAnimation)
            )
        }
        Text(text = stringResource(id = R.string.thanks), style = textStyle)
        Button(
            onClick = {
                context.startActivity(intent)
                activityFinish()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = black,
                contentColor = white
            ),
            shape = RoundedCornerShape(50),
            modifier = modifier.fillMaxWidth(0.5f)
        ) {
            Text(text = stringResource(id = R.string.restart), style = textStyle)
        }
    }
}

@Preview
@Composable
fun ExitPreview() {
    FormBuilderTheme {
        Surface(color = MaterialTheme.colors.background) {
            ExitScreen(activityFinish = { })
        }
    }
}