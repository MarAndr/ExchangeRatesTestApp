package com.example.exchangeratestestapppublic.db

object CurrencyContract {
    const val CURRENCY_RATES_TABLE_NAME = "CurrencyRatesModel"

    object CurrencyRatesColumn {
        const val ID = "id"
        const val TIMESTAMP = "timestamp"
        const val BASE = "base"
        const val QUOTE = "quote"
        const val RATE = "rate"
    }

    object CurrenciesColumn {
        const val ID = "id"
        const val TIMESTAMP = "timestamp"
        const val BASE = "base"
        const val QUOTE = "quote"
        const val RATE = "rate"
    }
}