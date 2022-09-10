package com.example.exchangeratestestapppublic.api.model

import java.util.Date

data class LatestCurrencyResponse(
    val id: Long = 0,
    val success: Boolean,
    val timestamp: Long?,
    val base: Symbol?,
    val date: Date?,
    val rates: Map<Symbol, Double>?,
)

