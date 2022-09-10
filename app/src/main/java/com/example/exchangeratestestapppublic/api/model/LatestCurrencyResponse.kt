package com.example.exchangeratestestapppublic.api.model

data class LatestCurrencyResponse(
    val id: Long = 0,
    val success: Boolean,
    val timestamp: Long?,
    val base: Symbol?,
    val date: String?,
    val rates: Map<Symbol, Double>?,
)

