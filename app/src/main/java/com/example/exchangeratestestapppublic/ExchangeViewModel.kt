package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {
    val repo = ExchangeRepository(ExchangeApi.getApi())
    private val _state = MutableStateFlow(LatestCurrencyResponse())
    val state: StateFlow<LatestCurrencyResponse> = _state

    fun getLatestCurrency() {
        viewModelScope.launch {
            _state.value = repo.getLatestCurrency("USD")
        }
    }
}