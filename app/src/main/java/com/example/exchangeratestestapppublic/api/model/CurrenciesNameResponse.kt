package com.example.exchangeratestestapppublic.api.model

data class CurrenciesNameResponse(
    val success: Boolean,
    val symbols: Map<Symbol, String>?
)
