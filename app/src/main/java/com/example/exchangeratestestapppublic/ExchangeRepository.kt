package com.example.exchangeratestestapppublic

import okhttp3.ResponseBody

class ExchangeRepository(private val retrofit: ExchangeApi) {
    suspend fun getLatestCurrency(): Currency {
        return retrofit.getLatestCurrency()
    }
}