package com.example.exchangeratestestapppublic.domain

import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.dao.CurrenciesListDao
import com.example.exchangeratestestapppublic.db.dao.CurrencyRatesDao
import com.example.exchangeratestestapppublic.domain.mapper.NamesDbMapper
import com.example.exchangeratestestapppublic.domain.mapper.RatesDbMapper
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import com.example.exchangeratestestapppublic.ui.Ordering
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class ExchangeRepository @Inject constructor(
    private val retrofit: ExchangeApi,
    private val currenciesDao: CurrencyRatesDao,
    private val currenciesListDao: CurrenciesListDao,
    private val namesDbMapper: NamesDbMapper,
    private val ratesDbMapper: RatesDbMapper,
) {

    suspend fun fetchLatestCurrency(base: Symbol) {
        val response = retrofit.getLatestCurrency(
            base = base.toString(), symbols = Symbol.values().joinToString(",")
        )
        if (!response.success || response.rates == null) {
            return
        }

        response.rates.forEach { (quote, rate) ->
            val ratesDbModel = ratesDbMapper.apiToDb(response, base, quote, rate)
            currenciesDao.addCurrencyRates(currencyRatesModel = ratesDbModel)
        }
    }

    fun getCurrencyRatesSorted(base: Symbol, ordering: Ordering): Flow<List<RatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getCurrencyRatesOrderByQuote(base.toString(), true)
            Ordering.QUOTE_DESC -> currenciesDao.getCurrencyRatesOrderByQuote(base.toString(), false)
            Ordering.RATE_ASC -> currenciesDao.getCurrencyRatesOrderByRate(base.toString(), true)
            Ordering.RATE_DESC -> currenciesDao.getCurrencyRatesOrderByRate(base.toString(), false)
        }.map { list -> list.map(ratesDbMapper::dbToDomainModel) }
    }

    fun getFavoriteCurrencyRates(base: Symbol, ordering: Ordering): Flow<List<RatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base.toString(), true)
            Ordering.QUOTE_DESC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base.toString(), false)
            Ordering.RATE_ASC -> currenciesDao.getFavoriteCurrencyRatesByRates(base.toString(), true)
            Ordering.RATE_DESC -> currenciesDao.getFavoriteCurrencyRatesByRates(base.toString(), false)
        }.map { list -> list.map(ratesDbMapper::dbToDomainModel) }
    }

    fun getCurrenciesList(): Flow<List<NameModel>> {
        return currenciesListDao.getCurrencies().map { currencies ->
            currencies.map(namesDbMapper::mapToDomain)
        }
    }

    suspend fun fetchCurrencyNamesList() {
        if (currenciesListDao.getCurrenciesList().isNotEmpty()) {
            return
        }

        val response = retrofit.getCurrencyNamesList()
        if (!response.success || response.symbols == null) {
            return
        }

        response.symbols.forEach { (symbol, name) ->
            val currencyNamesList = namesDbMapper.mapToDb(symbol, name)
            currenciesListDao.addCurrenciesList(currencyNamesList)
        }
    }

    suspend fun changeFavoriteField(favorite: Boolean, quote: String) {
        currenciesDao.changeFavoriteField(isQuoteFavorite = favorite, quote = quote)
    }
}
