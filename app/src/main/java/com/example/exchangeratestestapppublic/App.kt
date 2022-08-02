package com.example.exchangeratestestapppublic

import android.app.Application
import com.example.exchangeratestestapppublic.db.Database
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}