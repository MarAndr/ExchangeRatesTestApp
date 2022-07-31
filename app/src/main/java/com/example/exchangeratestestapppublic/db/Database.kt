package com.example.exchangeratestestapppublic.db

import android.content.Context
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