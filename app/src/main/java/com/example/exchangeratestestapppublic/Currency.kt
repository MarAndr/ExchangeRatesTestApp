package com.example.exchangeratestestapppublic

data class Currency(
    val base: String? = null,
    val rates: List<CurrencyRate>? = null
)
