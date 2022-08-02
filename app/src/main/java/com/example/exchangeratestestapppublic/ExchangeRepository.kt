package com.example.exchangeratestestapppublic

import android.util.Log
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesDao
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRepository @Inject constructor(
    private val retrofit: ExchangeApi,
    private val currenciesDao: CurrencyRatesDao,
    private val currenciesListDao: CurrenciesListDao
) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MY_TAG", "${throwable.message}")
    }

    suspend fun fetchLatestCurrency(base: String) = withContext(coroutineExceptionHandler) {
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

    fun getCurrencyRatesSorted(base: String, ordering: Ordering): Flow<List<CurrencyRatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getCurrencyRatesOrderByQuote(base, true)
            Ordering.QUOTE_DESC -> currenciesDao.getCurrencyRatesOrderByQuote(base, false)
            Ordering.RATE_ASC -> currenciesDao.getCurrencyRatesOrderByRate(base, true)
            Ordering.RATE_DESC -> currenciesDao.getCurrencyRatesOrderByRate(base, false)
        }
    }

    fun getFavoriteCurrencyRates(base: String, ordering: Ordering): Flow<List<CurrencyRatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base, true)
            Ordering.QUOTE_DESC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base, false)
            Ordering.RATE_ASC -> currenciesDao.getFavoriteCurrencyRatesByRates(base, true)
            Ordering.RATE_DESC -> currenciesDao.getFavoriteCurrencyRatesByRates(base, false)
        }
    }

    fun getCurrenciesList(): Flow<List<CurrenciesModel>> {
        return currenciesListDao.getCurrencies()
    }

    suspend fun fetchCurrencyNamesList() = withContext(coroutineExceptionHandler) {
        if (currenciesListDao.getCurrenciesList().isEmpty()) {
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

    suspend fun changeFavoriteField(favorite: Boolean, quote: String) {
        currenciesDao.changeFavoriteField(isQuoteFavorite = favorite, quote = quote)
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