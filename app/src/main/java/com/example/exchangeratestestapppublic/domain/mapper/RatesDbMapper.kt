package com.example.exchangeratestestapppublic.domain.mapper

import com.example.exchangeratestestapppublic.api.model.RatesApiModel
import com.example.exchangeratestestapppublic.db.dao.CurrencyRatesDao
import com.example.exchangeratestestapppublic.db.model.RatesDbModel
import com.example.exchangeratestestapppublic.domain.model.RatesModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import javax.inject.Inject
import kotlin.math.roundToInt

class RatesDbMapper @Inject constructor(
    private val ratesDao: CurrencyRatesDao,
) {

    suspend fun apiToDb(
        response: RatesApiModel,
        base: Symbol,
        quote: Symbol,
        rate: Double,
    ): RatesDbModel {
        return RatesDbModel(
            timestamp = response.timestamp ?: 0,
            base = base.toString(),
            quote = quote.toString(),
            rate = (rate * 1000.0).roundToInt() / 1000.0,
            isQuoteFavorite = ratesDao.getFavoriteField(quote.toString()) ?: false
        )
    }

    fun dbToDomainModel(currencyRateDbModel: RatesDbModel) = RatesModel(
        timestamp = currencyRateDbModel.timestamp,
        base = Symbol.valueOf(currencyRateDbModel.base),
        quote = Symbol.valueOf(currencyRateDbModel.quote),
        rate = currencyRateDbModel.rate,
        isQuoteFavorite = currencyRateDbModel.isQuoteFavorite,
    )
}