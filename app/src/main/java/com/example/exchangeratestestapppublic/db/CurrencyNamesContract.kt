package com.example.exchangeratestestapppublic.db

object CurrencyNamesContract {
    const val CURRENCIES_TABLE_NAME = "CurrenciesModel"

    object Column {
        const val ID = "id"
        const val SUCCESS = "success"
        const val TIMESTAMP = "timestamp"
        const val BASE = "base"
        const val RATES = "rates"
    }
}