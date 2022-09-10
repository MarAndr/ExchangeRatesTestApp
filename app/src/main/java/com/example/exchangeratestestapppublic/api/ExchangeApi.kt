package com.example.exchangeratestestapppublic.api

import com.example.exchangeratestestapppublic.api.model.CurrenciesNameResponse
import com.example.exchangeratestestapppublic.api.model.LatestCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: 6j9BqM9zJ06Ceyb2CZK8WGHFmDLVgwDT")
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): LatestCurrencyResponse

    @GET("exchangerates_data/symbols")
    @Headers("apikey: 6j9BqM9zJ06Ceyb2CZK8WGHFmDLVgwDT") // todo секретные ключи лучше не хранить в коде открыто
    suspend fun getCurrencyNamesList(): CurrenciesNameResponse
}