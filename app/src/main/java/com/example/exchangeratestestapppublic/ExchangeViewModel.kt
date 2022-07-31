package com.example.exchangeratestestapppublic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangeratestestapppublic.api.ExchangeApi
import com.example.exchangeratestestapppublic.db.CurrencyRatesModel
import com.example.exchangeratestestapppublic.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {
    private val repo =
        ExchangeRepository(ExchangeApi.getApi(), Database.instance.currencyRatesDao())

    fun getCurrencyRates(base: String?): Flow<List<CurrencyRatesModel>> {
        Log.d("MY_TAG", "${repo.getCurrencyRates("USD")}")
        return repo.getCurrencyRates(base ?: "")
    }

    private val _state = MutableStateFlow(emptyList<CurrencyRatesModel>())
    val state: StateFlow<List<CurrencyRatesModel>> = _state

    //    private val _currencyNames = MutableStateFlow(CurrencyName())
//    val currencyNames: StateFlow<CurrencyName> = _currencyNames
    private val _mainScreen = MutableStateFlow(MainScreenState())
    val mainScreen: StateFlow<MainScreenState> = _mainScreen

    private var mainScreenStateValue: MainScreenState
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


//    private fun getLatestCurrency() {
//        viewModelScope.launch {
//            _state.value = repo.getLatestCurrency("USD")
//        }
//    }
//
//    fun getCurrencyNamesList() {
//        viewModelScope.launch {
//            _currencyNames.value = repo.getCurrencyNamesList()
//        }
//    }

    fun changeScreen(screen: Screen) {
        mainScreenStateValue = mainScreenStateValue.copy(activeScreen = screen)
    }
}