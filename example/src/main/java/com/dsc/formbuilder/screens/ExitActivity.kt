package com.dsc.formbuilder.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dsc.formbuilder.R
import com.dsc.formbuilder.theme.FormBuilderTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class ExitActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormBuilderTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ExitScreen(animatedNavController = rememberAnimatedNavController())
                }
            }
        }
    }
}

@Composable
fun ExitScreen(modifier: Modifier = Modifier, animatedNavController: NavController) {
    val black = MaterialTheme.colors.onPrimary
    val white = MaterialTheme.colors.background
    val textStyle = MaterialTheme.typography.body1

    Column(
        modifier
            .padding(12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.like),
            contentDescription = "Thumbs Up"
        )
        Text(text = stringResource(id = R.string.thanks), style = textStyle)
        Button(
            onClick = {
                animatedNavController.navigate("intro")
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

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun ExitPreview() {
    FormBuilderTheme {
        Surface(color = MaterialTheme.colors.background) {
            ExitScreen(animatedNavController = rememberAnimatedNavController())
        }
    }
}