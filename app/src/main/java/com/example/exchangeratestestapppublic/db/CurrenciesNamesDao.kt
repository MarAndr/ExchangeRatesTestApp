package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {
    @Query("select * from ${CurrencyContract.TABLE_NAME}")
    fun getCurrencyNames(): Flow<CurrenciesModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencies(currencies: CurrenciesModel)
}