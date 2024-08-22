package com.application.paymatemateportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.ViewModelProvider
import com.application.paymatemateportal.ui.theme.PayMateMatePortalTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]
            val sharedPreferences =
                getSharedPreferences("com.application.paymatemateportal", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("mate_loggedIn", false)
            PayMateMatePortalTheme {
                StatusBar()
                var showSplash by rememberSaveable {
                    mutableStateOf(true)
                }
                val snackBarHostState = remember { SnackbarHostState() }
                if (stateViewModel.accessDenied.value) {
                    LoginScreen(snackBarHostState = snackBarHostState)
                } else {
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
                        enter = scaleIn()
                    ) {
                        if (isLoggedIn) {
                            MateDashboard(snackBarHostState = SnackbarHostState())
                        } else {
                            LoginScreen(
                                modifier = Modifier.padding(),
                                snackBarHostState = snackBarHostState
                            )
                        }
                    }
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