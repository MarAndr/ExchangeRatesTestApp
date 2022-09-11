package com.example.exchangeratestestapppublic.domain.model

data class RatesModel(
    val timestamp: Long,
    val base: Symbol,
    val quote: Symbol,
    val rate: Double,
    val isQuoteFavorite: Boolean = false
)