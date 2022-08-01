package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private val currencyRatesDao = Database.instance.currencyRatesDao()
    private val currenciesListDao = Database.instance.currenciesListDao()

    private val _currenciesList = MutableStateFlow(emptyList<String>())
    val currenciesList: StateFlow<List<String>> = _currenciesList

    private val repo = ExchangeRepository(
        retrofit = ExchangeApi.getApi(),
        currenciesDao = currencyRatesDao,
        currenciesListDao = currenciesListDao
    )
    private val _mainScreen = MutableStateFlow(PopularScreenState())
    val mainScreen: StateFlow<PopularScreenState> = _mainScreen

    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> =
        repo.getCurrencyRates(base)

//    fun getCurrenciesList(): Flow<List<CurrenciesModel>> = repo.getCurrenciesList()

    fun getCurrenciesList() {
        _currenciesList.value = currenciesListDao.getCurrenciesList().map {
            "${it.name}(${it.symbol})"
        }
    }

    fun quotes(base: String?): StateFlow<List<String>> = flow {
        repo.getCurrencyRates(base).collect { currency ->
            val state = currency.map {
                it.quote
            }
            emit(state)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = emptyList())

//    fun currenciesList(): StateFlow<List<String>> = flow {
//        repo.getCurrenciesList().collect { currency ->
//            val state = currency.map { currenciesModel ->
//                "${currenciesModel.name}(${currenciesModel.symbol})"
//            }
//            emit(state)
//        }
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = emptyList())

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
                repo.fetchLatestCurrency("USD")
                repo.fetchCurrencyNamesList()
                getCurrenciesList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun changeScreen(screen: Screen) {
        mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
    }
}