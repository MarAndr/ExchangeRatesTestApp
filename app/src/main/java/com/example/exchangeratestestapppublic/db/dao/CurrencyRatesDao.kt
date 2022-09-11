package com.example.exchangeratestestapppublic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangeratestestapppublic.db.model.RatesDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {

    @Query("select * from RatesDbModel where base = :base")
    fun getCurrencyRates(base: String?): Flow<List<RatesDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencyRates(currencyRatesModel: RatesDbModel)

    @Query(
        "update RatesDbModel set isQuoteFavorite = :isQuoteFavorite " +
            "where quote = :quote"
    )
    suspend fun changeFavoriteField(isQuoteFavorite: Boolean, quote: String)

    @Query("select isQuoteFavorite from RatesDbModel where quote = :quote")
    suspend fun getFavoriteField(quote: String): Boolean?

    @Query("select * from RatesDbModel where base = :base and isQuoteFavorite = 1 ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getFavoriteCurrencyRatesByQuote(
        base: String,
        isAsc: Boolean
    ): Flow<List<RatesDbModel>>

    @Query("select * from RatesDbModel where base = :base and isQuoteFavorite = 1 ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getFavoriteCurrencyRatesByRates(
        base: String,
        isAsc: Boolean
    ): Flow<List<RatesDbModel>>

    @Query("SELECT * FROM RatesDbModel where base = :base ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getCurrencyRatesOrderByQuote(base: String, isAsc: Boolean): Flow<List<RatesDbModel>>

    @Query("SELECT * FROM RatesDbModel where base = :base ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getCurrencyRatesOrderByRate(base: String, isAsc: Boolean): Flow<List<RatesDbModel>>
}