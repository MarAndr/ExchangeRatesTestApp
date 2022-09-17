package com.example.exchangeratestestapppublic.api

import java.util.*

data class LatestCurrencyResponse(
    val id: Long,
    val success: Boolean?,
    val timestamp: Long?,
    val base: String?,
    val date: Date?,
    val rates: Map<String, Double>?
)

