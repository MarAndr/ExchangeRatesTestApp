package com.example.exchangeratestestapppublic

import okhttp3.Response

class ExchangeRepository(private val retrofit: ExchangeApi) {
    suspend fun getLatestCurrency(): Currency{
        return retrofit.getLatestCurrency()
    }
}