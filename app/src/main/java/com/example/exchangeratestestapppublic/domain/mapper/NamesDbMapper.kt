package com.example.exchangeratestestapppublic.domain.mapper

import com.example.exchangeratestestapppublic.db.model.NamesDbModel
import com.example.exchangeratestestapppublic.domain.model.NameModel
import com.example.exchangeratestestapppublic.domain.model.Symbol
import javax.inject.Inject

class NamesDbMapper @Inject constructor() {

    fun mapToDomain(currencyDb: NamesDbModel) = NameModel(
        id = currencyDb.id,
        symbol = Symbol.valueOf(currencyDb.symbol),
        name = currencyDb.name,
    )

    fun mapToDb(symbol: Symbol, name: String) = NamesDbModel(
        id = 0,
        symbol = symbol.value,
        name = name
    )
}