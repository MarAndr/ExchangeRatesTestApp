package com.example.exchangeratestestapppublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Response

class ExchangeViewModel: ViewModel() {
    val repo = ExchangeRepository(ExchangeApi.getApi())
    private val _state = MutableStateFlow(emptyList<Currency>())
    val state: StateFlow<List<Currency>> = _state
    private val _stateCurrency = MutableStateFlow(Currency("", 0.0))
    val stateCurrency: StateFlow<Currency> = _stateCurrency

    fun getLatestCurrency(){
        viewModelScope.launch {
        _stateCurrency.value = repo.getLatestCurrency()
        }
    }
}