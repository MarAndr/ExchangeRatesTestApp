package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrenciesModel
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private val currencyRatesDao = Database.instance.currencyRatesDao()
    private val currenciesListDao = Database.instance.currenciesListDao()

    private val repo = ExchangeRepository(
        retrofit = ExchangeApi.getApi(),
        currenciesDao = currencyRatesDao,
        currenciesListDao = currenciesListDao
    )
    private val _mainScreen = MutableStateFlow(PopularScreenState())
    val mainScreen: StateFlow<PopularScreenState> = _mainScreen

    fun getFavoriteCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> =
        repo.getFavoriteCurrencyRates(base)


    private var mainScreenStateValue: PopularScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getRates()
                repo.fetchCurrencyNamesList()
                subscribeCurrencyNames()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun changeQuoteFavorite(isFavorite: Boolean, quote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRatesDao.changeFavoriteField(isFavorite, quote)
            getRates()
        }
    }

    fun changeOrder(ordering: Ordering) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreenStateValue = mainScreenStateValue.copy(ordering = ordering)
            getRates()
        }
    }

    fun changeScreen(screen: Screen) {
        viewModelScope.launch(Dispatchers.IO) {
            mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
            getRates()
        }
    }

    fun changeChosenCurrency(currency: CurrenciesModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.fetchLatestCurrency(currency.symbol)
            mainScreenStateValue = mainScreenStateValue.copy(chosenCurrency = currency.symbol)
            getRates()
        }
    }

    private fun subscribeCurrencyNames() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCurrenciesList().onEach { names ->
                mainScreenStateValue = mainScreenStateValue.copy(currenciesList = names)
            }.collect()
        }
    }

    private fun getRates() {
        mainScreenStateValue.chosenCurrency?.let { base ->
            repo.getCurrencyRatesSorted(
                base = base,
                ordering = mainScreenStateValue.ordering
            ).onEach { rates ->
                mainScreenStateValue = mainScreenStateValue.copy(currencyRates = rates)
            }.launchIn(viewModelScope)
        }
    }
}

enum class Ordering {
    QUOTE_ASC,
    QUOTE_DESC,
    RATE_DESC,
    RATE_ASC,
}