package com.example.checkit.data.db

import android.content.Context
import android.util.Log
import androidx.room.Room

object Database {
    lateinit var instance: CurrencyDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            CurrencyDatabase.DB_NAME
        ).build()
    }

}