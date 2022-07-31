package com.example.exchangeratestestapppublic.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(
    tableName = CurrencyContract.CURRENCY_RATES_TABLE_NAME,
    primaryKeys = [
        CurrencyContract.CurrencyRatesColumn.BASE,
        CurrencyContract.CurrencyRatesColumn.QUOTE
    ]
)
data class CurrencyRatesModel(
//    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
//    val id: Long = 0,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.TIMESTAMP)
    val timestamp: Date,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.BASE)
    val base: String,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.QUOTE)
    val quote: String,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.RATE)
    val rate: Double
)

@Entity(tableName = CurrencyNamesContract.CURRENCIES_TABLE_NAME)
data class CurrenciesModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
    val id: Long = 0,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
    val symbol: String,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
    val name: String,
    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
    val isFavorite: Boolean = false
)

