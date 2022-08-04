package com.example.exchangeratestestapppublic.api

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
    @Headers("apikey: 6j9BqM9zJ06Ceyb2CZK8WGHFmDLVgwDT")
    suspend fun getCurrencyNamesList(): CurrenciesNameResponse
}