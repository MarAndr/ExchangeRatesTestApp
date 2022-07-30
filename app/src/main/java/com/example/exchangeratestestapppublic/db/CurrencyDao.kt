package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangeratestestapppublic.CurrencyRate

@Dao
interface CurrencyDao {
    @Query("select * from ${CurrencyContract.TABLE_NAME}")
    fun getRates(): List<CurrencyRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRates(rates: List<CurrencyRate>)
}