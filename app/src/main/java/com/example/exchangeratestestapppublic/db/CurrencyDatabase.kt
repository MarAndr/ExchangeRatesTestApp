package com.example.exchangeratestestapppublic.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CurrencyRatesModel::class, CurrenciesModel::class],
    version = CurrencyDatabase.DB_VERSION
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyRatesDao(): CurrencyRatesDao

    companion object {
        const val DB_NAME = "currencyDatabase"
        const val DB_VERSION = 1
    }
}