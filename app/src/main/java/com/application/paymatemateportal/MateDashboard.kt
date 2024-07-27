package com.application.paymatemateportal

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
private fun Preview() {
    MateDashboard()
}


@Composable
fun MateDashboard(modifier: Modifier = Modifier, stateViewModel: StateViewModel = viewModel()) {
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = colorResource(id = R.color.app_theme_color)),
        ) {
            Text(
                text = "Pay Mate", fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(start = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = "Refresh"
                )
            }
        }
    }) { paddingValues ->
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.background_color))
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Wahaj Sajid", style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 15.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icon), contentDescription = "Mate Icon",
                modifier = Modifier
                    .padding(top = 15.dp)
                    .size(100.dp)
            )
            Text(
                text = "wahaj@1",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 5.dp)
            )

            HorizontalDivider(
                thickness = 2.dp,
                modifier = Modifier.padding(top = 15.dp),
                color = MaterialTheme.colors.onSecondary
            )
            Text(
                text = "Payable Dues: ",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 20.dp, start = 5.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.h5,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun CardPreview() {
    CardComposable()
}

@Composable
fun CardComposable(heading: String = "Wallet:", amount: String = "3000") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .background(
                    brush = Brush.linearGradient
                        (
                        colors = listOf(
                            colorResource(id = R.color.start_color),
                            colorResource(id = R.color.center_color),
                            colorResource(id = R.color.end_color),
                        ),
                        start = Offset(50f, 20f),
                        end = Offset(50f, 20f)
                    )
                )
        ) {
            Text(
                text = heading, style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Rs: ", fontFamily = FontFamily.Cursive)
                Text(
                    text = amount,
                    style = MaterialTheme.typography.h5,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}
