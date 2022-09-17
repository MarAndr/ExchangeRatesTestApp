package com.example.exchangeratestestapppublic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.exchangeratestestapppublic.db.model.CurrencyDbModel
import kotlinx.coroutines.flow.Flow

@Dao interface CurrenciesListDao {

    @Query("select * from CurrencyDbModel")
    fun getCurrencies(): Flow<List<CurrencyDbModel>>

    @Query("select * from CurrencyDbModel")
    suspend fun getCurrenciesList(): List<CurrencyDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrenciesList(currencies: List<CurrencyDbModel>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun setFavorite(currency: CurrencyDbModel)
}