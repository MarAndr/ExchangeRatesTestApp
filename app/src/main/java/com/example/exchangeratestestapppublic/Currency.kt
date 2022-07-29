package com.example.exchangeratestestapppublic

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currency(
    val name: String,
    val rate: Double
)
