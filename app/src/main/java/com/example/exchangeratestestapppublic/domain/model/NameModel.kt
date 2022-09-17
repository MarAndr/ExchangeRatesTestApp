package com.example.exchangeratestestapppublic.domain.model

data class NameModel(
    val id: Long,
    val symbol: Symbol,
    val name: String,
    val isFavourite: Boolean = false,
)