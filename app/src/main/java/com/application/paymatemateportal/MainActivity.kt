package com.application.paymatemateportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.application.paymatemateportal.ui.theme.PayMateMatePortalTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayMateMatePortalTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                    StatusBar()
                    var showSplash by rememberSaveable {
                        mutableStateOf(true)
                    }
                    LaunchedEffect(Unit) {
                        delay(2000)
                        showSplash = false
                    }
                    AnimatedVisibility(
                        visible = showSplash,
                        enter = scaleIn(),
                        exit = scaleOut()
                    ) {
                        SplashScreen(
                            modifier = Modifier
                                .padding()
                                .fillMaxSize()
                        )
                    }

                    AnimatedVisibility(
                        visible = !showSplash,
                        enter = scaleIn(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioLowBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        LoginScreen(
                            modifier = Modifier.padding(),
                            snackBarHostState = snackBarHostState
                        )
                    }
            }
        }
    }
}

@Composable
fun StatusBar() {
    val systemUiController = rememberSystemUiController()
    systemUiController.isStatusBarVisible = true
    systemUiController.setSystemBarsColor(color = colorResource(id = R.color.app_theme_color))
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PayMateMatePortalTheme {
        Greeting("Android")
    }
}