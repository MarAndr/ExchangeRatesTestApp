package com.example.exchangeratestestapppublic.api.model

import com.example.exchangeratestestapppublic.domain.model.Symbol
import java.util.Date

data class RatesApiModel(
    val id: Long = 0,
    val success: Boolean,
    val timestamp: Long?,
    val base: Symbol?,
    val date: Date?,
    val rates: Map<Symbol, Double>?,
)

