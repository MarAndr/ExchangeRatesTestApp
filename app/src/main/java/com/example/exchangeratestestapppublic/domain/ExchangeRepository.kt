package com.example.exchangeratestestapppublic.domain

import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.dao.CurrenciesListDao
import com.example.exchangeratestestapppublic.domain.mapper.NamesDbMapper
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExchangeRepository @Inject constructor(
    private val retrofit: ExchangeApi,
    private val currenciesListDao: CurrenciesListDao,
    private val namesDbMapper: NamesDbMapper,
) {

    suspend fun fetchCurrencyNamesList(): List<NameModel> {
        val response = retrofit.getCurrencyNamesList()
        if (response.symbols == null) {
            throw Exception("Symbols are null")
        }

        val dbModels = response.symbols.map { (symbol, name) ->
            namesDbMapper.mapToDb(symbol, name)
        }

        return withContext(Dispatchers.IO) {
            currenciesListDao.addCurrenciesList(dbModels)

            currenciesListDao.getCurrenciesList().map { namesDbMapper.mapToDomain(it) }
        }
    }

    suspend fun fetchLatestCurrency(base: Symbol): List<RatesModel> {
        val response = retrofit.getLatestCurrency(
            base = base.value,
            symbols = Symbol.values().joinToString(",") { it.value }
        )

        if (response.rates == null) {
            throw Exception("No rates")
        }

        return response.rates
            .map {
                RatesModel(
                    base = base,
                    it.key,
                    it.value,
                )
            }
    }

    suspend fun setFavourite(nameModel: NameModel, isFavourite: Boolean) {
        currenciesListDao.setFavorite(
            namesDbMapper.mapToDb(
                // mapping
            )
        )
    }
}
