package com.example.exchangeratestestapppublic

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {


//    access_key: l44cH4lb0vizrlUpGDWo3rEbT5Ec11SI

        @GET("/latest /latest\n" +
                "    ? access_key = l44cH4lb0vizrlUpGDWo3rEbT5Ec11SI\n" +
                "    & base = USD\n" +
                "    & symbols = GBP,JPY,EUR")
        suspend fun getLatestCurrency(): Currency



        companion object{
            fun getApi(): ExchangeApi{
                val httpClient = OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor())
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.exchangeratesapi.io/v1/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return retrofit.create()
            }
        }
}