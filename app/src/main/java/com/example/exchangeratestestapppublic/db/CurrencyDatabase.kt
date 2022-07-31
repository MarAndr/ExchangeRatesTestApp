package com.example.exchangeratestestapppublic.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = CurrencyDatabase.DB_VERSION)
abstract class CurrencyDatabase : RoomDatabase() {

//    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DB_NAME = "currencyDatabase"
        const val DB_VERSION = 1
    }
}