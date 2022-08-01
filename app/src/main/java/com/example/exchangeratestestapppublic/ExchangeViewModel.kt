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

    private val repo =
        ExchangeRepository(ExchangeApi.getApi(), Database.instance.currencyRatesDao())
    private val _mainScreen = MutableStateFlow(PopularScreenState())
    val mainScreen: StateFlow<PopularScreenState> = _mainScreen

    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> =
        repo.getCurrencyRates(base)

    fun quotes(base: String?): StateFlow<List<String>> = flow {
        repo.getCurrencyRates(base).collect { currency ->
            val state = currency.map {
                it.quote
            }
            emit(state)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = emptyList())

    private var mainScreenStateValue: PopularScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.fetchLatestCurrency("USD")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun changeScreen(screen: Screen) {
        mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
    }
}