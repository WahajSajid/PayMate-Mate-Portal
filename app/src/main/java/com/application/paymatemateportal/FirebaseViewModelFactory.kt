package com.application.paymatemateportal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirebaseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirebaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FirebaseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}