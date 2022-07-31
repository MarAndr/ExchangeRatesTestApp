package com.example.exchangeratestestapppublic.db

object CurrencyNamesContract {
    const val CURRENCIES_TABLE_NAME = "CurrenciesModel"

    object Column {
        const val ID = "id"
        const val SYMBOL = "symbol"
        const val NAME = "name"
        const val IS_FAVORITE = "isFavorite"
    }
}