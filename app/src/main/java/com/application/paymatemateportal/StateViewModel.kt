package com.application.paymatemateportal

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class StateViewModel:ViewModel() {
   private val _adminUidTextState = mutableStateOf("")
    var adminUidTextState = _adminUidTextState


    private val _mateIdTextFieldState = mutableStateOf("")
    var mateIdTextFieldState = _mateIdTextFieldState

}