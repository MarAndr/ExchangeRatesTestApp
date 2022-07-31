package com.example.exchangeratestestapppublic

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

    //    @GET("exchangerates_data/latest?symbols={GBP,JPY,EUR}")
    @GET("exchangerates_data/latest")
    suspend fun getLatestCurrency(
        @Query("base") base: String
    ): LatestCurrencyResponse

    @GET("exchangerates_data/symbols")
    suspend fun getCurrencyNamesList(): CurrencyName


    companion object {
        fun getApi(): ExchangeApi {
            val httpClient = OkHttpClient
                .Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
                })
                .addNetworkInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder().apply {
                        header("apikey", "L84JCatpD9MPVFV0wIKV8DIoBNdPzMop")
                    }.build()
                    chain.proceed(request)
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