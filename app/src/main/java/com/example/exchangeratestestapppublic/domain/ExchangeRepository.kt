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

    suspend fun fetchLatestCurrency(base: Symbol): List<RatesModel> {
        val response = retrofit.getLatestCurrency(
            base = base.value,
            symbols = Symbol.values().joinToString(",") { it.value }
        )
        if (response.rates == null) {
            throw Exception("No rates")
        }

        val ratesDbModel = response.rates.map { (quote, rate) ->
            ratesDbMapper.apiToDb(response, base, quote, rate)
        }
        currenciesDao.addCurrencyRates(currencyRatesModel = ratesDbModel)

        return currenciesDao.getCurrencyRates(base = base.value).map { ratesDbMapper.dbToDomainModel(it) }
    }

    fun getCurrencyRatesSorted(base: Symbol, ordering: Ordering): Flow<List<RatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getCurrencyRatesOrderByQuote(base.value, true)
            Ordering.QUOTE_DESC -> currenciesDao.getCurrencyRatesOrderByQuote(base.value, false)
            Ordering.RATE_ASC -> currenciesDao.getCurrencyRatesOrderByRate(base.value, true)
            Ordering.RATE_DESC -> currenciesDao.getCurrencyRatesOrderByRate(base.value, false)
        }.map { list -> list.map(ratesDbMapper::dbToDomainModel) }
    }

    fun getFavoriteCurrencyRates(base: Symbol, ordering: Ordering): Flow<List<RatesModel>> {
        return when (ordering) {
            Ordering.QUOTE_ASC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base.value, true)
            Ordering.QUOTE_DESC -> currenciesDao.getFavoriteCurrencyRatesByQuote(base.value, false)
            Ordering.RATE_ASC -> currenciesDao.getFavoriteCurrencyRatesByRates(base.value, true)
            Ordering.RATE_DESC -> currenciesDao.getFavoriteCurrencyRatesByRates(base.value, false)
        }.map { list -> list.map(ratesDbMapper::dbToDomainModel) }
    }

    fun getCurrenciesFlow(): Flow<List<NameModel>> {
        return currenciesListDao.getCurrencies().map { currencies ->
            currencies.map(namesDbMapper::mapToDomain)
        }
    }

    suspend fun fetchCurrencyNamesList(): List<NameModel> {
        val response = retrofit.getCurrencyNamesList()
        if (response.symbols == null) {
            throw Exception("Symbols are null")
        }

        val dbModels = response.symbols.map { (symbol, name) ->
            namesDbMapper.mapToDb(symbol, name)
        }

        currenciesListDao.addCurrenciesList(dbModels)

        return currenciesListDao.getCurrenciesList().map { namesDbMapper.mapToDomain(it) }
    }

    suspend fun changeFavoriteField(favorite: Boolean, quote: String) {
        currenciesDao.changeFavoriteField(isQuoteFavorite = favorite, quote = quote)
    }
}
