package com.application.paymatemateportal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    SplashScreen(modifier = Modifier.fillMaxSize())
}


@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.app_mate_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Developed by CODEWORKS",
                fontWeight = Bold,
                color = colorResource(id = R.color.app_theme_color),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 10.dp)
            )
        }
    }
}