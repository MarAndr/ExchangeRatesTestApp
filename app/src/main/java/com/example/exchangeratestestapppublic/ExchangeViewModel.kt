package com.example.exchangeratestestapppublic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.BufferedSource

class ExchangeViewModel: ViewModel() {
    val repo = ExchangeRepository(ExchangeApi.getApi())
    private val _state = MutableStateFlow(Currency())
    val state: StateFlow<Currency> = _state

    fun getLatestCurrency(){
        viewModelScope.launch {
        _state.value = repo.getLatestCurrency()
        }
    }
}