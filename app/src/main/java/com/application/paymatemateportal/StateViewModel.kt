package com.application.paymatemateportal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel

class StateViewModel : ViewModel() {

    //Login Screen States
    private val _adminUidTextState = mutableStateOf("")
    var adminUidTextState = _adminUidTextState


    private val _mateIdTextFieldState = mutableStateOf("")
    var mateIdTextFieldState = _mateIdTextFieldState


    private val _showSnackBar = mutableStateOf(false)
    var showSnackBar  = _showSnackBar

    private val _showDialog = mutableStateOf(false)
    var showDialog = _showDialog


    private val  _loginSuccessful = mutableStateOf(false)
    val loginSuccessful = _loginSuccessful

    val focusRequester1 =  FocusRequester()
    val focusRequester2 =  FocusRequester()

    //Mate Dashboard Screen States.
    private val _name = mutableStateOf("")
    var name  = _name

    private val _id = mutableStateOf("")
    private val id = _id

}