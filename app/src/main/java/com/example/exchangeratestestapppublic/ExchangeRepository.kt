package com.example.exchangeratestestapppublic

class ExchangeRepository(private val retrofit: ExchangeApi) {
    suspend fun getLatestCurrency(base: String): LatestCurrencyResponse {
        return retrofit.getLatestCurrency(base = base)
    }
}