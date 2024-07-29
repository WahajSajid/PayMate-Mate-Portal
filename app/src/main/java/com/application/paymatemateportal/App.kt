package com.application.paymatemateportal

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase

class App : Application() {
    lateinit var database: FirebaseDatabase
        private set
    override fun onCreate() {
        super.onCreate()
        val options = FirebaseOptions.Builder()
            .setApiKey("AIzaSyAZXl3iFNajkQTV3evrq3OM5G5Qb29taNo")
            .setApplicationId("1:454928281130:android:27a25a82d8d181ae7d1d53")
            .setDatabaseUrl("https://paymate-e1dab-default-rtdb.firebaseio.com")
            .build()
        val app =
            FirebaseApp.initializeApp(this, options)
        database = FirebaseDatabase.getInstance(app)
    }
}