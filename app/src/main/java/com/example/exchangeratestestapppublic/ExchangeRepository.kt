package com.example.exchangeratestestapppublic

import android.util.Log
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.*
import kotlinx.coroutines.flow.Flow

class ExchangeRepository(
    private val retrofit: ExchangeApi,
    private val currenciesDao: CurrencyRatesDao,
    private val currenciesListDao: CurrenciesListDao
) {

    suspend fun fetchLatestCurrency(base: String) {
        val response = retrofit.getLatestCurrency(
            base = base,
            symbols = Symbols.getSymbolsString()
        )
        if (response.success != false) {
            response.rates?.let {
                it.forEach { (quote, rate) ->
                    val currencyRateModel = CurrencyRatesModel(
                        timestamp = response.timestamp ?: 0,
                        base = base,
                        quote = quote,
                        rate = rate,
                        isQuoteFavorite = currenciesDao.getFavoriteField(quote) ?: false
                    )

                    currenciesDao.addCurrencyRates(currencyRatesModel = currencyRateModel)
                }
            }
        }
    }

    fun getCurrencyRates(base: String? = null): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRates(base)
    }

    fun getCurrencyRatesSorted(base: String, ordering: Ordering): Flow<List<CurrencyRatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getCurrencyRatesSorted(
                base,
                "${CurrencyRatesContract.CurrencyRatesColumn.QUOTE} ASC"
            )
            Ordering.QUOTE_DESC -> currenciesDao.getCurrencyRatesSorted(
                base,
                "${CurrencyRatesContract.CurrencyRatesColumn.QUOTE} DESC"
            )
            Ordering.RATE_ASC -> currenciesDao.getCurrencyRatesSorted(
                base,
                "${CurrencyRatesContract.CurrencyRatesColumn.RATE} ASC"
            )
            Ordering.RATE_DESC -> currenciesDao.getCurrencyRatesSorted(
                base,
                "${CurrencyRatesContract.CurrencyRatesColumn.RATE} DESC"
            )
        }
    }

    fun getCurrencyRatesSortedByAscQuote(base: String): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRatesSortedByAscQuote(base)
    }

    fun getCurrencyRatesSortedByDescQuote(base: String): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRatesSortedByDescQuote(base)
    }

    fun getCurrencyRatesSortedByAscRate(base: String): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRatesSortedByAscRate(base)
    }

    fun getCurrencyRatesSortedByDescRate(base: String): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRatesSortedByDescRate(base)
    }

    fun getFavoriteCurrencyRates(base: String? = null): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getFavoriteCurrencyRates(base)
    }

//    fun getCurrenciesList(): Flow<List<CurrenciesModel>> {
//        return currenciesListDao.getCurrencies()
//    }

    suspend fun fetchCurrencyNamesList() {
        if (currenciesListDao.getCurrenciesList().isEmpty()) {
            Log.d("MY_TAG", "fetchCurrencyNamesList:")
            val response = retrofit.getCurrencyNamesList()
            if (response.success != false) {
                response.symbols?.let {
                    it.forEach { (symbol, name) ->
                        val currencyNamesList =
                            CurrenciesModel(id = 0, symbol = symbol, name = name)
                        currenciesListDao.addCurrenciesList(currencyNamesList)
                    }

                }
            }
        }
    }
}

enum class Symbols {
    USD,
    EUR,
    GBP,
    CNY,
    CHF,
    JPY,
    UAH,
    RUB,
    SEK,
    TRY,
    SGD,
    CAD,
    DKK,
    KRW,
    BRL,
    INR,
    PLN,
    AMD;

    companion object {
        fun getSymbolsString() = values().joinToString(",")
    }
}