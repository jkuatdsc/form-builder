package com.dsc.formbuilder.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dsc.formbuilder.R
import com.dsc.formbuilder.animations.PulsingButton
import com.dsc.formbuilder.theme.FormBuilderTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class IntroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormBuilderTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigations()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigations() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = "intro") {
        composable(
            "intro",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 })
            }
        ) { IntroScreen(
            onStartClick = { navController.navigate("form_inputs") }) }

        composable(
            "exit",
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 })
            }
        ) {
            ExitScreen(animatedNavController = navController)
        }
    }
}


@Composable
fun IntroScreen(modifier: Modifier = Modifier, onStartClick: ()->Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(150.dp))

        Box(
            modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            //this box prevents the animation from mis-positioning the other elements in the column
            PulsingButton(onStartClicked = onStartClick)
        }
        Box(
            modifier = modifier
                .size(300.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.validate),
                    style = MaterialTheme.typography.body1,
                )
                Spacer(modifier = modifier.width(24.dp))

                CustomSwitch()
            }
        }
    }
}

@Composable
fun CustomSwitch(
    modifier: Modifier = Modifier,
    width: Dp = 60.dp,
    height: Dp = 25.dp,
    thumbColor: Color = MaterialTheme.colors.background,
    checkedTrackColor: Color = MaterialTheme.colors.onPrimary,
    uncheckedTrackColor: Color = MaterialTheme.colors.onSurface,
    spaceBetweenThumbAndTrackEdge: Dp = 1.dp,
    cornerSize: Int = 60,
    thumbSize: Dp = 45.dp
) {
    //disable ripple effect on custom switch
    val interactionSource = remember {
        MutableInteractionSource()
    }
    //state of the switch
    var switchOn by remember { mutableStateOf(true) }

    //move thumb horizontally using bias +ve f->end, -ve f->start
    val alignment by animateAlignmentAsState(if (switchOn) 2f else -2f)

    //outer rectangle->track
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .clip(RoundedCornerShape(cornerSize))
            .background(color = if (switchOn) checkedTrackColor else uncheckedTrackColor)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { switchOn = !switchOn },
        contentAlignment = alignment
    ) {
        //thumb
        Canvas(modifier = modifier.size(thumbSize)) {
            val thumbRadius = (height / 2) - spaceBetweenThumbAndTrackEdge
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx()
            )
        }
    }
}

//Todo: implement validate on screen button

@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetValue = targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }

}

@Preview
@Composable
fun IntroPreview() {
    FormBuilderTheme {
        Surface(color = MaterialTheme.colors.background) {
           IntroScreen(onStartClick = { })
        }
    }
}