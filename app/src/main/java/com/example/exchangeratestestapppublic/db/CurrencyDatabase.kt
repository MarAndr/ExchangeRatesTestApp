package com.example.exchangeratestestapppublic.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exchangeratestestapppublic.db.dao.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.model.CurrencyDbModel

@Database(
    entities = [CurrencyDbModel::class],
    version = CurrencyDatabase.DB_VERSION
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currenciesListDao(): CurrenciesListDao

    companion object {

        const val DB_NAME = "currencyDatabase"
        const val DB_VERSION = 2
    }
}