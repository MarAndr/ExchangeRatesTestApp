package com.example.exchangeratestestapppublic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import kotlinx.coroutines.flow.Flow

@Dao interface CurrenciesListDao {

    @Query("select * from CurrenciesModel")
    fun getCurrencies(): Flow<List<CurrenciesModel>>

    @Query("select * from CurrenciesModel")
    fun getCurrenciesList(): List<CurrenciesModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrenciesList(currencies: CurrenciesModel)
}