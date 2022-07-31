package com.example.exchangeratestestapppublic

import com.example.exchangeratestestapppublic.api.CurrenciesNameResponse
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrencyRatesDao
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import kotlinx.coroutines.flow.Flow

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
                        timestamp = response.timestamp ?: 0,
                        base = base,
                        quote = quote,
                        rate = rate
                    )

                    currenciesDao.addCurrencyRates(currencyRatesModel = currencyRateModel)
                }
            }
        }
    }

    fun getCurrencyRates(base: String? = null): Flow<List<CurrencyRatesModel>> {
        return currenciesDao.getCurrencyRates(base)
    }

    suspend fun getCurrencyNamesList(): CurrenciesNameResponse {
        return retrofit.getCurrencyNamesList()
    }
}