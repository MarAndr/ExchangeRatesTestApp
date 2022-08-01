package com.example.exchangeratestestapppublic.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME,
    primaryKeys = [
        CurrencyRatesContract.CurrencyRatesColumn.BASE,
        CurrencyRatesContract.CurrencyRatesColumn.QUOTE
    ]
)
data class CurrencyRatesModel(
//    @ColumnInfo(name = CurrencyContract.CurrencyRatesColumn.ID)
//    val id: Long = 0,
    @ColumnInfo(name = CurrencyRatesContract.CurrencyRatesColumn.TIMESTAMP)
    val timestamp: Long,
    @ColumnInfo(name = CurrencyRatesContract.CurrencyRatesColumn.BASE)
    val base: String,
    @ColumnInfo(name = CurrencyRatesContract.CurrencyRatesColumn.QUOTE)
    val quote: String,
    @ColumnInfo(name = CurrencyRatesContract.CurrencyRatesColumn.RATE)
    val rate: Double,
    @ColumnInfo(name = CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE)
    val isQuoteFavorite: Boolean = false
)

@Entity(tableName = CurrencyNamesContract.CURRENCIES_TABLE_NAME)
data class CurrenciesModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CurrencyNamesContract.Column.ID)
    val id: Long = 0,
    @ColumnInfo(name = CurrencyNamesContract.Column.SYMBOL)
    val symbol: String,
    @ColumnInfo(name = CurrencyNamesContract.Column.NAME)
    val name: String
)

