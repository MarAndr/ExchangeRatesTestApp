package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {
    //    @Query("select * from ${CurrencyContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyContract.CurrencyRatesColumn.BASE} = :base")
//    @Query("select * from ${CurrencyContract.CURRENCY_RATES_TABLE_NAME}")
    @Query("select * from CurrencyRatesModel where base = :base")
    suspend fun getCurrencyRates(base: String): Flow<List<CurrencyRatesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencyRates(currencyRatesModel: CurrencyRatesModel)
}