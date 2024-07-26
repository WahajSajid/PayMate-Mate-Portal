package com.application.paymatemateportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.application.paymatemateportal.ui.theme.PayMateMatePortalTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyAZXl3iFNajkQTV3evrq3OM5G5Qb29taNo")
            .setApplicationId("1:454928281130:android:27a25a82d8d181ae7d1d53")
            .setDatabaseUrl("https://paymate-e1dab-default-rtdb.firebaseio.com")
            .build()
        val app = FirebaseApp.initializeApp(this, options)
        val database = FirebaseDatabase.getInstance(app)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sharedPreferences =
                getSharedPreferences("com.application.paymatemateportal", MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("mate_loggedIn", false)
            PayMateMatePortalTheme {
                StatusBar()
                var showSplash by rememberSaveable {
                    mutableStateOf(true)
                }
                val snackBarHostState = remember { SnackbarHostState() }
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
                        if(isLoggedIn){
                            MateDashboard()
                        } else{
                            LoginScreen(
                                modifier = Modifier.padding(),
                                database,
                                snackBarHostState = snackBarHostState
                            )
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