package com.example.exchangeratestestapppublic.api

import java.util.Date

data class LatestCurrencyResponse(
    val id: Long = 0,
    val success: Boolean? = null,
    val timestamp: Long? = null,
    val base: String? = null,
    val date: Date? = null,
    val rates: Map<String, Double>? = null
)

