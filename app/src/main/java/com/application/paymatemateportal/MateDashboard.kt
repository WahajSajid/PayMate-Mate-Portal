package com.application.paymatemateportal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.paymatemateportal.ui.theme.PayMateMatePortalTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview
@Composable
private fun Preview() {
    MateDashboard(snackBarHostState = SnackbarHostState())
}


@Composable
fun MateDashboard(
    modifier: Modifier = Modifier,
    stateViewModel: StateViewModel = viewModel(),
    snackBarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val viewModel: FirebaseViewModel =
        viewModel(factory = FirebaseViewModelFactory(context.applicationContext as App))
    val database = viewModel.database
    val sharedPreferences =
        context.getSharedPreferences("com.application.paymatemateportal", MODE_PRIVATE)
    //Getting id and adminUid from sharedPreferences
    val id = sharedPreferences.getString("mate_id", "null").toString()
    val adminUid = sharedPreferences.getString("uid", "null").toString()
    val matePath = "Mate: $id"
    Scaffold(topBar = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
                .fillMaxWidth()
                .height(50.dp)
                .background(color = colorResource(id = R.color.app_theme_color)),
        ) {
            Text(
                text = "PayMate", fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(start = 5.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                stateViewModel.showLoadingDataDialog.value = true
                mateAvailableOrNot(
                    adminUid,
                    matePath,
                    context,
                    database,
                    sharedPreferences,
                    stateViewModel,
                    snackBarHostState
                )
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = "Refresh"
                )
            }
        }
    }, snackbarHost = {
        PayMateMatePortalTheme {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        }
    }) { paddingValues ->


        stateViewModel.dialogTitle.value = "Loading data..."
        if (stateViewModel.showLoadingDataDialog.value) {
            DialogBox()
        }
        mateAvailableOrNot(
            adminUid,
            matePath,
            context,
            database,
            sharedPreferences,
            stateViewModel,
            snackBarHostState
        )
        if (stateViewModel.showAccessDeniedDialog.value) {
            ShowAlertDialog()
        }
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.background_color))
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stateViewModel.name.value, style = MaterialTheme.typography.h5,
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
                text = stateViewModel.mate_id.value,
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
            CardComposable(heading = "Rent", amount = stateViewModel.rent_amount.value)
            CardComposable(heading = "Other", amount = stateViewModel.other_amount.value)
            CardComposable(heading = "Wallet", amount = stateViewModel.wallet_amount.value)
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
    Column (modifier = Modifier.padding(20.dp)){

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
                    modifier = Modifier.padding(start = 7.dp,top = 7.dp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Rs: ", fontFamily = FontFamily.Cursive, fontSize = 18.sp)
                    Text(
                        text = amount,
                        style = MaterialTheme.typography.h5,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }

}

//This function will show the data which has been fetched from the database when the mate login.
private fun showExistingData(
    sharedPreferences: SharedPreferences,
    stateViewModel: StateViewModel,
) {
    stateViewModel.name.value = sharedPreferences.getString("name", "").toString()
    stateViewModel.wallet_amount.value = sharedPreferences.getString("wallet_amount", "").toString()
    stateViewModel.other_amount.value = sharedPreferences.getString("other_amount", "").toString()
    stateViewModel.rent_amount.value = sharedPreferences.getString("rent_amount", "").toString()
}


//This function will show load will call the function to load the data for each.
private fun loadData(
    adminUid: String,
    matePath: String,
    database: FirebaseDatabase,
    sharedPreferences: SharedPreferences,
    context: Context,
    stateViewModel: StateViewModel,
) {
    val databaseReference =
        database.getReference("admin_profiles").child(adminUid).child("Mates").child(matePath)
    val pathStrings = arrayOf("name", "wallet_amount", "rent_amount", "other_amount", "mate_id")
    for (i in pathStrings.indices) {
        getData(
            databaseReference.child(pathStrings[i]),
            pathStrings[i],
            sharedPreferences,
            stateViewModel,
            pathStrings[i],
            context
        )
    }
}


//This will retrieve the data from the database.
private fun getData(
    reference: DatabaseReference,
    localDatabaseReference: String,
    sharedPreferences: SharedPreferences,
    stateViewModel: StateViewModel,
    state: String,
    context: Context,
) {
    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val data = snapshot.value.toString()
            when (state) {
                "name" -> stateViewModel.name.value = data
                "wallet_amount" -> stateViewModel.wallet_amount.value = data
                "rent_amount" -> stateViewModel.rent_amount.value = data
                "other_amount" -> stateViewModel.other_amount.value = data
                "mate_id" -> stateViewModel.mate_id.value = data
            }
            sharedPreferences.edit().putString(localDatabaseReference, data).apply()
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    })
}

private fun mateAvailableOrNot(
    adminUid: String,
    matePath: String,
    context: Context,
    database: FirebaseDatabase,
    sharedPreferences: SharedPreferences,
    stateViewModel: StateViewModel,
    hostState: SnackbarHostState,
) {
    if (NetworkUtil.isNetworkAvailable(context)) {
        HasInternetAccess.hasInternetAccess(object : HasInternetAccessCallBack {
            override fun onInternetAvailable() {
                val databaseReference = database.getReference("admin_profiles")
                databaseReference.child(adminUid)
                    .addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {

                                if (snapshot.child("Mates").child(matePath).exists()) {
                                    loadData(
                                        adminUid,
                                        matePath,
                                        database,
                                        sharedPreferences,
                                        context,
                                        stateViewModel
                                    )
                                    stateViewModel.showLoadingDataDialog.value = false
                                } else {
                                    stateViewModel.showAccessDeniedDialog.value = true
                                    stateViewModel.accessDeniedDialogMessage.value =
                                        "We are sorry, Admin has been removed you."
                                }
                            } else {
                                stateViewModel.showAccessDeniedDialog.value = true
                                stateViewModel.accessDeniedDialogMessage.value =
                                    "We are sorry, Admin have been deleted the account."
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        }
                    })


            }

            override fun onInternetNotAvailable() {
                CoroutineScope(Dispatchers.Main).launch {
                    stateViewModel.showLoadingDataDialog.value = false
                    stateViewModel.showSnackBar.value = true
                    showSnackBar(
                        hostState,
                        stateViewModel,
                        "Connection Timeout! Check your connection and refresh again"
                    )
                    showExistingData(sharedPreferences, stateViewModel)
                }
            }
        })
    } else {
        stateViewModel.showSnackBar.value = true
        showSnackBar(
            hostState,
            stateViewModel,
            "No Internet, Please connect to the internet and refresh again"
        )
        stateViewModel.showLoadingDataDialog.value = false
        showExistingData(sharedPreferences, stateViewModel)
    }

}

@Preview
@Composable
fun AlertDialogPreview() {
    ShowAlertDialog()
}

@Composable
fun ShowAlertDialog(
    stateViewModel: StateViewModel = viewModel(),
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        buttons = {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences(
                            "com.application.paymatemateportal",
                            MODE_PRIVATE
                        )
                        sharedPreferences.edit().putBoolean("mate_loggedIn", false).apply()
                        stateViewModel.accessDenied.value = true
                    },
                ) {
                    Text(text = "Ok")
                }
            }
        },
        title = {
            Text(
                text = "Access Denied",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = { Text(text = stateViewModel.accessDeniedDialogMessage.value, fontSize = 18.sp) }
    )
}
