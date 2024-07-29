package com.application.paymatemateportal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase

class FirebaseViewModel(application: Application): AndroidViewModel(application) {
    val database:FirebaseDatabase = (application as App).database
}