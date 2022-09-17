package com.example.exchangeratestestapppublic.domain.model

data class RatesModel(
    val base: Symbol,
    val quote: Symbol,
    val rate: Double,
)