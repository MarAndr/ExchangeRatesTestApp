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
    suspend fun getFavoriteField(quote: String): Boolean?

    @Query("select * from ${CurrencyRatesContract.CURRENCY_RATES_TABLE_NAME} where ${CurrencyRatesContract.CurrencyRatesColumn.BASE} = :base and ${CurrencyRatesContract.CurrencyRatesColumn.IS_QUOTE_FAVORITE} = 1")
    fun getFavoriteCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>>
}