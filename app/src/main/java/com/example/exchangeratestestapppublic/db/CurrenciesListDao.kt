package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesListDao {
    @Query("select * from ${CurrencyNamesContract.CURRENCIES_TABLE_NAME}")
    fun getCurrencies(): Flow<List<CurrenciesModel>>

    @Query("select * from ${CurrencyNamesContract.CURRENCIES_TABLE_NAME}")
    fun getCurrenciesList(): List<CurrenciesModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrenciesList(currencies: CurrenciesModel)
}