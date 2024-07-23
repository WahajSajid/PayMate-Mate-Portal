package com.application.paymatemateportal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    SplashScreen(modifier = Modifier.fillMaxSize())
}


@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Surface {
            Image(
                painter = painterResource(id = R.drawable.app_mate_logo),
                contentDescription = "Logo",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Developed by CODEWORKS",
            color = colorResource(id = R.color.app_theme_color),
        )
    }
}