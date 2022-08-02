package com.example.exchangeratestestapppublic.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: L84JCatpD9MPVFV0wIKV8DIoBNdPzMop")
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): LatestCurrencyResponse

    @GET("exchangerates_data/symbols")
    @Headers("apikey: L84JCatpD9MPVFV0wIKV8DIoBNdPzMop")
    suspend fun getCurrencyNamesList(): CurrenciesNameResponse
}