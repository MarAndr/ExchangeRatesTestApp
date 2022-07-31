package com.example.exchangeratestestapppublic

import java.util.*


data class LatestCurrencyResponse(
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val base: String? = null,
    val date: Date? = null,
    val rates: Map<String, Double>? = null
)

data class CurrencyName(
    val success: Boolean? = null,
    val symbols: Map<String, String>? = null
)

