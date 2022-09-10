package com.example.exchangeratestestapppublic.domain

import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.api.model.Symbol
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.db.dao.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.dao.CurrencyRatesDao
import com.example.exchangeratestestapppublic.ui.Ordering
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class ExchangeRepository @Inject constructor(
    private val retrofit: ExchangeApi,
    private val currenciesDao: CurrencyRatesDao,
    private val currenciesListDao: CurrenciesListDao,
) {

    suspend fun fetchLatestCurrency(base: String) {
        val response = retrofit.getLatestCurrency(
            base = base,
            symbols = Symbol.values().joinToString(",")
        )
        if (response.success) {
            response.rates?.let {
                it.forEach { (quote, rate) ->
                    val currencyRateModel = CurrencyRatesModel(
                        timestamp = response.timestamp ?: 0,
                        base = base,
                        quote = quote.name,
                        rate = (rate * 1000.0).roundToInt() / 1000.0,
                        isQuoteFavorite = currenciesDao.getFavoriteField(quote.name) ?: false
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

    suspend fun fetchCurrencyNamesList() {
        if (currenciesListDao.getCurrenciesList().isEmpty()) {
            val response = retrofit.getCurrencyNamesList()

            if (response.success) {
                response.symbols?.let {
                    it.forEach { (symbol, name) ->
                        val currencyNamesList = CurrenciesModel(id = 0, symbol = symbol.name, name = name)
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
