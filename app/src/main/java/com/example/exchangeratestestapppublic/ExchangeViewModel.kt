package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private val currencyRatesDao = Database.instance.currencyRatesDao()
    private val currenciesListDao = Database.instance.currenciesListDao()

//    private val _currenciesList = MutableStateFlow(emptyList<CurrenciesModel>())
//    val currenciesList: StateFlow<List<CurrenciesModel>> = _currenciesList

    private val repo = ExchangeRepository(
        retrofit = ExchangeApi.getApi(),
        currenciesDao = currencyRatesDao,
        currenciesListDao = currenciesListDao
    )
    private val _mainScreen = MutableStateFlow(PopularScreenState())
    val mainScreen: StateFlow<PopularScreenState> = _mainScreen

    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> =
        repo.getCurrencyRates(base)

    fun getFavoriteCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> =
        repo.getFavoriteCurrencyRates(base)


    private var mainScreenStateValue: PopularScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        viewModelScope.launch {
            currencyRatesDao.changeFavoriteField(isFavorite, quote)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.fetchCurrencyNamesList()
//                repo.getCurrencyRatesSorted()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun changeOrder(ordering: Ordering) {
        viewModelScope.launch {
            mainScreenStateValue.chosenCurrency?.let {
                repo.getCurrencyRatesSorted(
                    base = it,
                    ordering = ordering
                ).collectLatest {
//                    mainScreenStateValue = mainScreenStateValue.copy(currencyRates = it)
                }
            }

        }


    }

    fun changeScreen(screen: Screen) {
        mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
    }

    fun changeChosenCurrency(currency: CurrenciesModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchLatestCurrency(currency.symbol)
        }
        mainScreenStateValue = mainScreenStateValue.copy(chosenCurrency = currency.symbol)
    }
}

enum class Ordering() {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC,
    RATE_ASC,
}