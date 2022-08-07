package com.example.exchangeratestestapppublic.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRatesDao {
    @Query("select * from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base")
    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCurrencyRates(currencyRatesModel: CurrencyRatesModel)

    @Query(
        "update ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} set ${CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE} = :isQuoteFavorite " +
                "where ${CurrencyRatesContract.CurrencyRatesColumn.QUOTE} = :quote"
    )
    suspend fun changeFavoriteField(isQuoteFavorite: Boolean, quote: String)

    @Query("select ${CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE} from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.QUOTE} = :quote")
    fun getFavoriteField(quote: String): Boolean?

    @Query("select * from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base and ${CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE} = 1 ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getFavoriteCurrencyRatesByQuote(
        base: String,
        isAsc: Boolean
    ): Flow<List<CurrencyRatesModel>>

    @Query("select * from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base and ${CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE} = 1 ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getFavoriteCurrencyRatesByRates(
        base: String,
        isAsc: Boolean
    ): Flow<List<CurrencyRatesModel>>

    @Query("SELECT * FROM ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base ORDER BY CASE WHEN :isAsc = 1 THEN quote END ASC, CASE WHEN :isAsc = 0 THEN quote END DESC")
    fun getCurrencyRatesOrderByQuote(base: String, isAsc: Boolean): Flow<List<CurrencyRatesModel>>

    @Query("SELECT * FROM ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base ORDER BY CASE WHEN :isAsc = 1 THEN rate END ASC, CASE WHEN :isAsc = 0 THEN rate END DESC")
    fun getCurrencyRatesOrderByRate(base: String, isAsc: Boolean): Flow<List<CurrencyRatesModel>>
}