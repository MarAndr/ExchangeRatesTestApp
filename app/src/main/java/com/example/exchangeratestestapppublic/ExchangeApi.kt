package com.example.exchangeratestestapppublic

import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

        @GET("/latest")
        suspend fun getLatestCurrency(): Currency

//        @GET("/jokes/categories")
//        suspend fun getJokesCategories(): List<JokeCategory>
//
//        @GET("/jokes/random")
//        suspend fun getRandomJokeByCategory(
//            @Query("category") category: String
//        ): NorrisJoke

        companion object{
            fun getApi(): ExchangeApi{
                val httpClient = OkHttpClient.Builder()
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.exchangeratesapi.io")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                return retrofit.create()
            }
        }
}