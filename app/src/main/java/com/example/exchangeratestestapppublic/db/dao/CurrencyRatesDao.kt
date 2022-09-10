package com.example.exchangeratestestapppublic.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {

    @Query("select * from CurrencyRatesModel where base = :base")
    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencyRates(currencyRatesModel: CurrencyRatesModel)

    @Query(
        "update CurrencyRatesModel set isQuoteFavorite = :isQuoteFavorite " +
            "where quote = :quote"
    )
    suspend fun changeFavoriteField(isQuoteFavorite: Boolean, quote: String)

    @Query("select isQuoteFavorite from CurrencyRatesModel where quote = :quote")
    fun getFavoriteField(quote: String): Boolean?

    @Query("select * from CurrencyRatesModel where base = :base and isQuoteFavorite = 1 ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getFavoriteCurrencyRatesByQuote(
        base: String,
        isAsc: Boolean
    ): Flow<List<CurrencyRatesModel>>

    @Query("select * from CurrencyRatesModel where base = :base and isQuoteFavorite = 1 ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getFavoriteCurrencyRatesByRates(
        base: String,
        isAsc: Boolean
    ): Flow<List<CurrencyRatesModel>>

    @Query("SELECT * FROM CurrencyRatesModel where base = :base ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getCurrencyRatesOrderByQuote(base: String, isAsc: Boolean): Flow<List<CurrencyRatesModel>>

    @Query("SELECT * FROM CurrencyRatesModel where base = :base ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getCurrencyRatesOrderByRate(base: String, isAsc: Boolean): Flow<List<CurrencyRatesModel>>
}