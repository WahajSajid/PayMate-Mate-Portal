package com.application.paymatemateportal

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    LoginScreen(modifier = Modifier.fillMaxSize())
}


@Composable
fun LoginScreen(modifier: Modifier = Modifier, stateViewModel: StateViewModel = viewModel()) {
    Box(modifier = with(modifier) {
        fillMaxSize()
            .paint(
                painterResource(R.drawable.background),
                contentScale = ContentScale.FillBounds
            )
    })
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, top = 210.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Welcome! ",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = FontFamily.Cursive
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 150.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hey, Mate \uD83D\uDC4B",
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
            TextInputsComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 15.dp, end = 15.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.app_theme_color),
                        shape = RoundedCornerShape(26.dp)
                    ), value = "admin"
            )

            TextInputsComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 15.dp, end = 15.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.app_theme_color),
                        shape = RoundedCornerShape(26.dp)
                    ), value = "mate"
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputsComposable(
    modifier: Modifier = Modifier,
    value: String,
    stateViewModel: StateViewModel = viewModel(),
) {
    TextField(
        value = if (value == "admin") stateViewModel.adminUidTextState.value else stateViewModel.mateIdTextFieldState.value,
        onValueChange = {
            if (value == "admin") stateViewModel.adminUidTextState.value =
                it else stateViewModel.mateIdTextFieldState.value = it
        },
        leadingIcon = {
            Icon(
                painter = if (value == "admin") painterResource(id = R.drawable.baseline_admin_panel_settings_24) else painterResource(
                    id = R.drawable.baseline_person_pin_24
                ), contentDescription = "icon"
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        placeholder = { if (value == "admin") Text(text = "Admin Id") else Text(text = "Mate Id") },
        modifier = modifier

    )
}