package com.application.paymatemateportal

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.paymatemateportal.ui.theme.PayMateMatePortalTheme
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    LoginScreen(modifier = Modifier.fillMaxSize(), snackBarHostState = SnackbarHostState())
}


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    stateViewModel: StateViewModel = viewModel(),
    snackBarHostState: SnackbarHostState,
) {
    if (stateViewModel.loginSuccessful.value) {
        MateDashboard(snackBarHostState = snackBarHostState)
    } else {
        val context = LocalContext.current
        val viewModel :FirebaseViewModel = viewModel(factory = FirebaseViewModelFactory(context.applicationContext as App))
        val database = viewModel.database
        Scaffold(snackbarHost = {
            PayMateMatePortalTheme {
                SnackbarHost(
                    hostState = snackBarHostState,
                )
            }
        }, floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (!inputFieldsEmptyOrNot(
                            stateViewModel.adminUidTextState.value,
                            stateViewModel.mateIdTextFieldState.value
                        )
                    ) {
                        if (NetworkUtil.isNetworkAvailable(context)) {
                            stateViewModel.dialogTitle.value = "Signing in..."
                            stateViewModel.showSigningInDialog.value = true
                            HasInternetAccess.hasInternetAccess(object : HasInternetAccessCallBack {
                                override fun onInternetAvailable() {
                                    loginMate(
                                        stateViewModel.mateIdTextFieldState.value,
                                        stateViewModel.adminUidTextState.value,
                                        context,
                                        database,
                                        object : LoginSuccessfulCallBack {
                                            override fun onLoginSuccessful() {
                                                stateViewModel.showSigningInDialog.value = false
                                                stateViewModel.showSnackBar.value = true
                                                stateViewModel.loginSuccessful.value = true
                                            }

                                            override fun onLoginFailed() {
                                                stateViewModel.showSigningInDialog.value = false
                                                stateViewModel.showSnackBar.value = true
                                                showSnackBar(
                                                    snackBarHostState,
                                                    stateViewModel,
                                                    message = "Invalid credentials supplied."
                                                )
                                            }
                                        })
                                }

                                override fun onInternetNotAvailable() {
                                    stateViewModel.showSnackBar.value = true
                                    stateViewModel.showSigningInDialog.value = false
                                    showSnackBar(
                                        snackBarHostState,
                                        stateViewModel,
                                        message = "Connection timeout, check your internet connection and try again."
                                    )
                                }
                            })

                        } else {
                            stateViewModel.showSnackBar.value = true
                            showSnackBar(
                                snackBarHostState,
                                stateViewModel,
                                message = "No Internet, Please connect to the internet and try again."
                            )
                        }
                    } else Toast.makeText(
                        context,
                        "Please fill all the fields ",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                },
                containerColor = colorResource(id = R.color.app_theme_color),
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_login_24),
                    contentDescription = "Login"
                )
            }
        }) { paddingValues ->
            if (stateViewModel.showSigningInDialog.value) DialogBox()
            Box(modifier = with(modifier.padding(paddingValues)) {
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
                            .focusRequester(stateViewModel.focusRequester1)
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
                            .focusRequester(stateViewModel.focusRequester2)
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
    }
}

private fun loginMate(
    mateId: String,
    adminId: String,
    context: Context,
    database: FirebaseDatabase,
    callBack: LoginSuccessfulCallBack,
) {
    val sharedPreferences =
        context.getSharedPreferences("com.application.paymatemateportal", MODE_PRIVATE)
    val enteredMateUid = mateId.trim()
    val enteredAdminUid = adminId.trim()
    val matePath = "Mate: $enteredMateUid"
    val databaseReference =
        database.getReference("admin_profiles").child(enteredAdminUid).child("Mates")
            .child(matePath)
    databaseReference.child("mate_id").get().addOnSuccessListener {
        val id = it.value.toString()
        if (enteredMateUid == id) {
            sharedPreferences.edit().putString("mate_id", id).apply()
            sharedPreferences.edit().putString("uid", enteredAdminUid).apply()
            sharedPreferences.edit().putBoolean("mate_loggedIn", true).apply()
            val getData =
                GetDataFromDatabase.GetDataFromDatabase(databaseReference, sharedPreferences)
            getData.getAllData()
            callBack.onLoginSuccessful()
        } else {
            callBack.onLoginFailed()
        }
    }
    databaseReference.get().addOnFailureListener {
        Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
    }
}

fun showSnackBar(
    hostState: SnackbarHostState,
    stateViewModel: StateViewModel,
    message: String,
) {
    CoroutineScope(Dispatchers.Main).launch {
        if (stateViewModel.showSnackBar.value) {
            hostState.showSnackbar(message)
            stateViewModel.showSnackBar.value = false
        }
    }
}

private fun inputFieldsEmptyOrNot(
    adminId: String,
    mateId: String,
): Boolean {
    val empty: Boolean = adminId == "" || mateId == ""
    return empty
}

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
        modifier = modifier,
        keyboardOptions = if (value == "admin") KeyboardOptions.Default.copy(imeAction = ImeAction.Next) else KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = if (value == "admin") KeyboardActions(onNext = { stateViewModel.focusRequester2.requestFocus() }) else KeyboardActions(
            onDone = null
        )

    )
}

@Preview
@Composable
fun DialogBox(stateViewModel: StateViewModel = viewModel()) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false)
    ) {
        Card(modifier = Modifier.width(150.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp),
                    color = colorResource(id = R.color.app_theme_color),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
                Text(
                    text = stateViewModel.dialogTitle.value, fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }
    }
}