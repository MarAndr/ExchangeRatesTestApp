package com.example.exchangeratestestapppublic.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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

    companion object {
        fun getApi(): ExchangeApi {
            val httpClient = OkHttpClient
                .Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                })
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.apilayer.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create()
        }
    }
}