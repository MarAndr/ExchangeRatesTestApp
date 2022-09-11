package com.example.exchangeratestestapppublic.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = [
        "base",
        "quote",
    ]
)
data class RatesDbModel(
    val timestamp: Long,
    val base: String,
    val quote: String,
    val rate: Double,
    val isQuoteFavorite: Boolean = false
)

@Entity
data class NamesDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val symbol: String,
    val name: String
)

