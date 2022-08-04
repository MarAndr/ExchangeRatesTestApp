package com.example.exchangeratestestapppublic.di

import android.app.Application
import androidx.room.Room
import com.example.exchangeratestestapppublic.ExchangeRepository
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.CurrencyDatabase
import com.example.exchangeratestestapppublic.db.CurrencyRatesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun bindRetrofit(httpClient: OkHttpClient): ExchangeApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun bindClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            })
            .build()
    }

    @Provides
    @Singleton
    fun bindDatabase(context: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            context,
            CurrencyDatabase::class.java,
            CurrencyDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun bindCurrencyRatesDao(db: CurrencyDatabase): CurrencyRatesDao {
        return db.currencyRatesDao()
    }

    @Provides
    @Singleton
    fun bindCurrencyListDao(db: CurrencyDatabase): CurrenciesListDao {
        return db.currenciesListDao()
    }

    @Provides
    @Singleton
    fun bindRepository(
        api: ExchangeApi,
        currencyRatesDao: CurrencyRatesDao,
        currenciesListDao: CurrenciesListDao
    ): ExchangeRepository {
        return ExchangeRepository(
            retrofit = api,
            currenciesDao = currencyRatesDao,
            currenciesListDao = currenciesListDao
        )
    }
}