package com.example.exchangeratestestapppublic.api

import com.example.exchangeratestestapppublic.api.model.NamesApiModel
import com.example.exchangeratestestapppublic.api.model.RatesApiModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("exchangerates_data/latest")
    @Headers("apikey: 6j9BqM9zJ06Ceyb2CZK8WGHFmDLVgwDT")
    suspend fun getLatestCurrency(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): RatesApiModel

    @GET("exchangerates_data/symbols")
    @Headers("apikey: 6j9BqM9zJ06Ceyb2CZK8WGHFmDLVgwDT") // todo секретные ключи лучше не хранить в коде открыто
    suspend fun getCurrencyNamesList(): NamesApiModel
}