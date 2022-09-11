package com.example.exchangeratestestapppublic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangeratestestapppublic.db.model.NamesDbModel
import kotlinx.coroutines.flow.Flow

@Dao interface CurrenciesListDao {

    @Query("select * from NamesDbModel")
    fun getCurrencies(): Flow<List<NamesDbModel>>

    @Query("select * from NamesDbModel")
    suspend fun getCurrenciesList(): List<NamesDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrenciesList(currencies: NamesDbModel)
}