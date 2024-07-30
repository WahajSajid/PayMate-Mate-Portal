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

    private val _showSigningInDialog = mutableStateOf(false)
    var showSigningInDialog = _showSigningInDialog


    private val  _loginSuccessful = mutableStateOf(false)
    val loginSuccessful = _loginSuccessful

    val focusRequester1 =  FocusRequester()
    val focusRequester2 =  FocusRequester()

    //Mate Dashboard Screen States.
    private val _name = mutableStateOf("")
    var name  = _name

    private val _wallet = mutableStateOf("")
    var wallet_amount = _wallet


    private val _rent = mutableStateOf("")
    var rent_amount = _rent


    private val _otherDues = mutableStateOf("")
    var other_amount = _otherDues

    private val _showAccessDeniedDialog = mutableStateOf(false)
    var showAccessDeniedDialog = _showAccessDeniedDialog

    private val _accessDenied = mutableStateOf(false)
    var accessDenied =  _accessDenied

    private val _dialogTitle = mutableStateOf("")
    var dialogTitle = _dialogTitle

    private val _accessDeniedDialogMessage = mutableStateOf("")
    var accessDeniedDialogMessage = _accessDeniedDialogMessage

    private val _showLoadingDataDialog = mutableStateOf(true)
    var showLoadingDataDialog = _showLoadingDataDialog

    private val _id = mutableStateOf("")
    var mate_id = _id

}