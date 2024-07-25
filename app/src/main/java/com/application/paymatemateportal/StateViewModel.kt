package com.application.paymatemateportal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel

class StateViewModel : ViewModel() {
    private val _adminUidTextState = mutableStateOf("")
    var adminUidTextState = _adminUidTextState


    private val _mateIdTextFieldState = mutableStateOf("")
    var mateIdTextFieldState = _mateIdTextFieldState


    private val _showSnackBar = mutableStateOf(false)
    var showSnackBar  = _showSnackBar

    private val _showDialog = mutableStateOf(false)
    var showDialog = _showDialog

    val focusRequester1 =  FocusRequester()
    val focusRequester2 =  FocusRequester()

}