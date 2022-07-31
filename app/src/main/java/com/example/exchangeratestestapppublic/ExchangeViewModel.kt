package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {
    private val repo = ExchangeRepository(ExchangeApi.getApi())

    private val _state = MutableStateFlow(LatestCurrencyResponse())
    val state: StateFlow<LatestCurrencyResponse> = _state
    private val _mainScreen = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreen

    private var mainScreenStateValue: MainScreenState
        get() = _mainScreen.value
        set(value) {
            _mainScreen.value = value
        }

    init {
        try {
            getLatestCurrency()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun getLatestCurrency() {
        viewModelScope.launch {
            _state.value = repo.getLatestCurrency("USD")
        }
    }

    fun changeScreen(screen: Screen) {
        mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
    }
}