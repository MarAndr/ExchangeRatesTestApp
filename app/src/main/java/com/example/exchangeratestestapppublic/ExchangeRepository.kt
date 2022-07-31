package com.example.exchangeratestestapppublic

import com.example.exchangeratestestapppublic.api.CurrenciesNameResponse
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrencyRatesDao
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import kotlinx.coroutines.flow.Flow
import java.util.*

class ExchangeRepository(
    private val retrofit: ExchangeApi,
    private val currenciesDao: CurrencyRatesDao,
) {

    suspend fun fetchLatestCurrency(base: String) {
        val response = retrofit.getLatestCurrency(base = base)
        if (response.success != false) {
            response.rates?.let {
                it.forEach { (quote, rate) ->
                    val currencyRateModel = CurrencyRatesModel(
                        timestamp = Date(response.timestamp ?: 0),
                        base = base,
                        quote = quote,
                        rate = rate
                    )

                    currenciesDao.addCurrencyRates(currencyRatesModel = currencyRateModel)
                }
            }
        }
    }

    suspend fun getCurrencyRates(base: String): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRates(base)
    }

    suspend fun getCurrencyNamesList(): CurrenciesNameResponse {
        return retrofit.getCurrencyNamesList()
    }
}