package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {
    @Query("select * from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base")
    fun getCurrencyRates(base: String): Flow<List<CurrencyRatesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencyRates(currencyRatesModel: CurrencyRatesModel)
}